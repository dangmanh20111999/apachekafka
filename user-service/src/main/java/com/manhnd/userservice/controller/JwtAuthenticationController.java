package com.manhnd.userservice.controller;

import com.manhnd.userservice.model.User;
import com.manhnd.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping(value = "/{ids}")
	public ResponseEntity<Mono<UserDTO>> getUserById(@PathVariable String ids) {
		return ResponseEntity.ok(userService.getUserById(ids));
	}

	@PostMapping
	public ResponseEntity<Mono<User>> saveUser(@RequestBody UserDTO userDTO) {
		return ResponseEntity.ok(userService.saveUser(userDTO));
	}
}
