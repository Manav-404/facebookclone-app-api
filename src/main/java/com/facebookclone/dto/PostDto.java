package com.facebookclone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
	private long id;
	private String caption;
	private long user_id;
	private String user_name;
	
	
	
	
	@Builder
	public PostDto(long id, String caption, long user_id , String user_name) {
		super();
		this.id = id;
		this.caption = caption;
		this.user_id = user_id;
		this.user_name=user_name;
	}
	
	
	

}
