package com.manhnd.profileservice.controller;


import com.manhnd.profileservice.dto.ProfileDTO;
import com.manhnd.profileservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;




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
}
