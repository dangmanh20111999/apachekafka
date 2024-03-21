package com.manhnd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployController {

	@GetMapping(value = "/demo")
	public String HelloWorld() {
		return "Hello World";
	}
}
