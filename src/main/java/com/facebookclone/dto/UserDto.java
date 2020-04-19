package com.facebookclone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private long id;
	private String email;
	private String signInToken;
	
	@Builder
	public UserDto(long id, String email, String password ,String signInToken) {
		this.id = id;
		this.email = email;
		this.signInToken=signInToken;
	}
	
	

}
