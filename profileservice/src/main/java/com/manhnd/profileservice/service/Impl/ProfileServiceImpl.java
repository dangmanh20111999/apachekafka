package com.manhnd.profileservice.service.Impl;

import com.google.gson.Gson;
import com.manhnd.commonservice.commons.CommonException;
import com.manhnd.commonservice.utils.Constant;
import com.manhnd.profileservice.dto.ProfileDTO;
import com.manhnd.profileservice.event.EventProducer;
import com.manhnd.profileservice.repository.ProfileRepository;
import com.manhnd.profileservice.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    Gson gson = new Gson();
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    EventProducer eventProducer;
    @Override
    public Flux<ProfileDTO> getAllProfiles() {


        return profileRepository.findAll()
                .map(ProfileDTO::entityToDto)
                .switchIfEmpty(Mono.error(new CommonException("PF01","Empty profile list !", HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<ProfileDTO> getProfileById(Long id) {
        log.info(String.valueOf(new CommonException("00","SS", HttpStatus.OK)));
        return profileRepository.findById(id)
                .map(ProfileDTO::entityToDto)
                .switchIfEmpty(Mono.error(new CommonException("PF03","Empty profile list !", HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<Boolean> checkDuplicateEmail(String email) {
        return profileRepository.findByEmail(email)
                .flatMap(profile -> Mono.just(true))
                .switchIfEmpty(Mono.just(false));
    }

    @Override
    public Mono<ProfileDTO> saveProfile(ProfileDTO profileDTO) {
        return checkDuplicateEmail(profileDTO.getEmail()).flatMap(aBoolean -> {
            if (Boolean.TRUE.equals(aBoolean)) {
                return Mono.error(new CommonException("PF02","Duplicate profile !", HttpStatus.BAD_REQUEST));
            }
            else {
                profileDTO.setStatus(Constant.STATUS_PROFILE_PENDING);
                return createProfile(profileDTO);
            }
        });
    }

    @Override
    public Mono<ProfileDTO> updateStatusProfile(ProfileDTO profileDTO) {
        return getDetailProfileByEmail(profileDTO.getEmail())
                .map(ProfileDTO::dtoToEntity)
                .flatMap(profile -> {
                    profile.setStatus(profileDTO.getStatus());
                    return profileRepository.save(profile);
                })
                .map(ProfileDTO::entityToDto)
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<ProfileDTO> updateInitialBalance(ProfileDTO profileDTO) {
        return getDetailProfileByEmail(profileDTO.getEmail())
                .map(ProfileDTO::dtoToEntity)
                .map(ProfileDTO::entityToDto)
                .doOnSuccess(dto -> {
                    dto.setInitialBalance(profileDTO.getInitialBalance());
                    eventProducer.send(Constant.UPDATE_INITIALBLANCE_TOPIC,gson.toJson(dto)).subscribe();
                });
    }

    @Override
    public Mono<ProfileDTO> updateRole(ProfileDTO profileDTO) {
        return getDetailProfileByEmail(profileDTO.getEmail())
                .map(ProfileDTO::dtoToEntity)
                .flatMap(profile -> {
                    profile.setRole(profileDTO.getRole());
                    return profileRepository.save(profile);
                })
                .map(ProfileDTO::entityToDto)
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    public Mono<ProfileDTO> getDetailProfileByEmail(String email){
        return profileRepository.findByEmail(email)
                .map(ProfileDTO::entityToDto)
                .switchIfEmpty(Mono.error(new CommonException("PF03", "Profile not found", HttpStatus.NOT_FOUND)));
    }

    public Mono<ProfileDTO> createProfile(ProfileDTO profileDTO) {
        return Mono.just(profileDTO)
                .map(ProfileDTO::dtoToEntity)
                .flatMap(profile -> profileRepository.save(profile))
                .map(ProfileDTO::entityToDto)
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .doOnSuccess(dto -> {
                    if(Objects.equals(dto.getStatus(), Constant.STATUS_PROFILE_PENDING)) {
                        dto.setInitialBalance(profileDTO.getInitialBalance());
                        eventProducer.send(Constant.PROFILE_ONBOARDING_TOPIC,gson.toJson(dto)).subscribe();
                    }
        });
    }
}
