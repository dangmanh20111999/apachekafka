package com.manhnd.profileservice.service;

import com.manhnd.profileservice.dto.ProfileDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileService {

    Flux<ProfileDTO> getAllProfiles();

    Mono<ProfileDTO> getProfileById(Long id);

    Mono<Boolean> checkDuplicateEmail(String email);

    Mono<ProfileDTO> saveProfile(ProfileDTO profileDTO);

}
