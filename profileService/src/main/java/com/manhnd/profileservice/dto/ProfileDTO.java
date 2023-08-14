package com.manhnd.profileservice.dto;

import com.manhnd.profileservice.model.Profile;
import lombok.Data;

@Data
public class ProfileDTO {
	private Long id;
	private String email;
	private String name;
	private String status;
	private String role;
	private double initialBalance;

	public static ProfileDTO entityToDto(Profile profile) {
		ProfileDTO dto = new ProfileDTO();
		dto.setId(profile.getId());
		dto.setEmail(profile.getEmail());
		dto.setRole(profile.getRole());
		dto.setName(profile.getName());
		dto.setStatus(profile.getStatus());
		return dto;
	}

	public static Profile dtoToEntity(ProfileDTO profileDTO) {
		Profile entity = new Profile();
		entity.setId(profileDTO.getId());
		entity.setName(profileDTO.getName());
		entity.setEmail(profileDTO.getEmail());
		entity.setRole(profileDTO.getRole());
		entity.setStatus(profileDTO.getStatus());
		return entity;
	}
}
