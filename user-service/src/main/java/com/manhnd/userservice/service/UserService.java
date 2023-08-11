package com.manhnd.userservice.service;

import com.manhnd.userservice.model.UserDTO;

import reactor.core.publisher.Flux;

public interface UserService {

	Flux<UserDTO> getAllUser();
}
