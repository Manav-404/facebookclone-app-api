package com.facebookclone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {

private long id;
	
	private String fname;
	
	private String lname;
	
	private String city;
	
	private long user_id;
	
	private String imagePath;

	

	@Builder
	public ProfileDto(long id, String fname, String lname, String city, long user_id, String imagePath) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.city = city;
		this.user_id = user_id;
		this.imagePath = imagePath;
	}
	
	
}
