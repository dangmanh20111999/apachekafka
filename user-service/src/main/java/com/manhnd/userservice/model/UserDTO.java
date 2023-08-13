package com.manhnd.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
	static UUID uuid= UUID.randomUUID();
	private String ids;
	private String firstname;
	private String lastname;
	private String email;
	private int phonenumber;
	private String authoritynames;
	private String username;
	private String address;
	private String password;


	public static UserDTO entityToDto(User user) {
		UserDTO dto = new UserDTO();
		dto.setIds(user.getIds());
		dto.setAddress(user.getAddress());
		dto.setAuthoritynames(user.getAuthoritynames());
		dto.setEmail(user.getEmail());
		dto.setFirstname(user.getFirstname());
		dto.setLastname(user.getLastname());
		dto.setPhonenumber(user.getPhonenumber());
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		return dto;
	}

	public static User dtoToEntity(UserDTO userDto) {
		User entity = new User();
		entity.setAddress(userDto.getAddress());
		entity.setAuthoritynames(userDto.getAuthoritynames());
		entity.setEmail(userDto.getEmail());
		entity.setFirstname(userDto.getFirstname());
		entity.setIds(userDto.getIds());
		entity.setLastname(userDto.getLastname());
		entity.setPassword(userDto.getPassword());
		entity.setPhonenumber(userDto.getPhonenumber());
		entity.setUsername(userDto.getUsername());
		return entity;
	}
}
