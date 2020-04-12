package com.facebookclone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private long id;
	private String email;
	
	@Builder
	public UserDto(long id, String email, String password) {
		this.id = id;
		this.email = email;
	}
	
	

}
