package com.manhnd.profileservice.repository;


import com.manhnd.profileservice.model.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProfileRepository extends ReactiveCrudRepository<Profile, Long> {

    @Query(value = "SELECT * FROM PROFILE WHERE EMAIL = :email", nativeQuery = true)
    Mono<Profile> findByEmail(String email);
}
