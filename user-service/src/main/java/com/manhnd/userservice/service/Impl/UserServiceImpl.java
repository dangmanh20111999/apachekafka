package com.manhnd.userservice.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manhnd.userservice.model.UserDTO;
import com.manhnd.userservice.repository.UserRepository;
import com.manhnd.userservice.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepo;

	@Override
	public Flux<UserDTO> getAllUser() {
		return userRepo.findAll().map(user -> UserDTO.entityToDto(user)).switchIfEmpty(Mono.error(new RuntimeException("123")));
	}

}
