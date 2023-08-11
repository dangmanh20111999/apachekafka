package com.manhnd.userservice.controller;

import com.manhnd.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manhnd.userservice.model.UserDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;




@RestController
@RequestMapping("/api/v1/users")
public class JwtAuthenticationController {

	@Autowired
	private UserService userService;
	@GetMapping(value = "/getAllUser")
	public ResponseEntity<Flux<UserDTO>> getAllUser() {

		return ResponseEntity.ok(userService.getAllUser());
	}
	
}
