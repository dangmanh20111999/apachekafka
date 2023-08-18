package com.manhnd.profileservice.controller;


import com.manhnd.commonservice.utils.Constant;
import com.manhnd.profileservice.dto.ProfileDTO;
import com.manhnd.profileservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<Flux<ProfileDTO>>getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<ProfileDTO>> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @GetMapping(value = "/checkDuplicateEmail/{email}")
    public ResponseEntity<Mono<Boolean>> checkDuplicateEmail(@PathVariable String email) {
        return ResponseEntity.ok(profileService.checkDuplicateEmail(email));
    }

    @PostMapping
    public ResponseEntity<Mono<ProfileDTO>> saveProfile(@RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.saveProfile(profileDTO));
    }

    @PostMapping(value = "/updateInitialBalanceByEmail")
    public ResponseEntity<Mono<ProfileDTO>> updateInitialBalanceByEmail(@RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.updateInitialBalance(profileDTO));
    }

    @PostMapping(value = "/fluxDTO")
    public ResponseEntity<Flux<ProfileDTO>> fluxDTO() {
        Flux<ProfileDTO> fluxDto = profileService.getAllProfiles()
                .filter(m -> m.getRole().equals("ADMIN")).collectList().flatMapMany(Flux::fromIterable);
        return ResponseEntity.ok(fluxDto);
    }

    @PostMapping(value = "/monoDTO")
    public ResponseEntity<Mono<ProfileDTO>> monoDTO() {
        Mono<ProfileDTO> monoDto = profileService.getAllProfiles()
                .filter(m -> m.getRole().equals("ADMIN") && m.getId() == 1).singleOrEmpty();
        return ResponseEntity.ok(monoDto);
    }
}
