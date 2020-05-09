package com.facebookclone.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.facebookclone.dto.PostDto;
import com.facebookclone.model.Post;


public interface PostService {
	
	PostDto createPost(String text, String token);
	String postPic(MultipartFile file , long postId );
	List<PostDto>  getPostOfFriends(long userId);
	List<PostDto> getWallPosts(long userId);
	

}
