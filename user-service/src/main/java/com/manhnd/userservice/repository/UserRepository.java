package com.manhnd.userservice.repository;


import com.manhnd.userservice.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

}
