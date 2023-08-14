package com.manhnd.userservice.model;


public class JwtResponse {

	private final String token;
	private final User user;

	public JwtResponse(String token, User user) {
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return this.token;
	}

	public User getUser() {
		return user;
	}	

}
