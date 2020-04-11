package com.facebookclone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	private String text;
	private long id;
	
	@Builder
	public CommentDto(String text, long id) {
		super();
		this.text = text;
		this.id = id;
	}
	
	
	
}
