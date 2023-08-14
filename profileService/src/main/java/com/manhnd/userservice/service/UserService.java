package com.manhnd.userservice.service;

import com.manhnd.userservice.model.User;
import com.manhnd.userservice.model.UserDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

	Flux<UserDTO> getAllUser();
	Mono<UserDTO> getUserById(String id);
	Mono<User> saveUser(UserDTO userDTO);
}
