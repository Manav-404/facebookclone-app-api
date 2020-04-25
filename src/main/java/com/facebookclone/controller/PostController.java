package com.facebookclone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.facebookclone.dto.PostDto;
import com.facebookclone.model.Post;
import com.facebookclone.service.PostService;
import com.facebookclone.utils.RestResponse;

@RequestMapping("/api/post")
@RestController
public class PostController {
	
	@Autowired
	PostService postService;
	
	@PostMapping("/create")
	public RestResponse create_post(@RequestBody Post post ,@RequestParam("file") MultipartFile file) {
		PostDto dto = postService.createPost(post ,file);
		return RestResponse.builder().data(dto).status(true).build();
	}
	
	
	@Scheduled(fixedDelay = 25000)
	@GetMapping("/friends/{userId}")
	public RestResponse getFriendspost(@PathVariable("userId")Long userId) {
		List<PostDto> dto = postService.getPostOfFriends(userId);
		return RestResponse.builder().data(dto).status(true).build();
		
	}
	
	
	@GetMapping("/wall/{userId}")
	public RestResponse getWallPosts(@PathVariable("userId")Long userId) {
		List<PostDto> dto = postService.getWallPosts(userId);
		return RestResponse.builder().data(dto).data(true).build();
	}
	
	
}
	
	
