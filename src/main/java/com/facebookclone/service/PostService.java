package com.facebookclone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facebookclone.dto.PostDto;
import com.facebookclone.model.Post;


public interface PostService {
	
	PostDto createPost(Post post);
	List<PostDto>  getPostOfFriends(long userId);
	List<PostDto> getWallPosts(long userId);
	

}
