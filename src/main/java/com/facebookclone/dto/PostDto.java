package com.facebookclone.dto;

import java.util.List;

import com.facebookclone.model.Comment;

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
	private String path;
	private String profile;
	private List<CommentDto> comments;
	
	
	
	
	@Builder
	public PostDto(long id, String caption, long user_id , String user_name , String path , List<CommentDto> comments , String profile) {
		this.id = id;
		this.caption = caption;
		this.user_id = user_id;
		this.user_name=user_name;
		this.path = path;
		this.comments = comments;
		this.profile = profile;
	}
	
	
	

}
