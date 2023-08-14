package com.manhnd.profileservice.service.Impl;

import com.manhnd.profileservice.dto.ProfileDTO;
import com.manhnd.profileservice.repository.ProfileRepository;
import com.manhnd.profileservice.service.ProfileService;
import com.manhnd.profileservice.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Flux<ProfileDTO> getAllProfiles() {
        return profileRepository.findAll()
                .map(dto -> ProfileDTO.entityToDto(dto))
                .switchIfEmpty(Mono.error(new RuntimeException("123")));
    }

    @Override
    public Mono<ProfileDTO> getProfileById(Long id) {
        return profileRepository.findById(id)
                .map(dto -> ProfileDTO.entityToDto(dto))
                .switchIfEmpty(Mono.error(new RuntimeException("456")));
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
                return Mono.error(new RuntimeException("Duplicate Profile"));
            }
            else {
                profileDTO.setStatus(Constant.STATUS_PROFILE_PENDING);
                return createProfile(profileDTO);
            }
        });
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
                    }
                })
                ;
    }
}
