package com.facebookclone.dto;

import com.facebookclone.model.Profile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	private String text;
	private long id;
	private ProfileDto profile;
	
	@Builder
	public CommentDto(String text, long id , ProfileDto profile) {
		this.text = text;
		this.id = id;
		this.profile = profile;
	}
	
	
	
}
