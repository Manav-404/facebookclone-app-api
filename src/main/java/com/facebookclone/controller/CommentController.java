package com.facebookclone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebookclone.dto.CommentDto;
import com.facebookclone.model.Comment;
import com.facebookclone.service.CommentService;
import com.facebookclone.utils.RestResponse;

@RequestMapping("/api/comment")
@RestController
public class CommentController {
	
	@Autowired
	CommentService service;
	
	

	@GetMapping("/get/postId")
	public RestResponse getComment(@PathVariable("postId") Long postId) {
	List<CommentDto> dto = service.getCommentByPostId(postId);
	return RestResponse.builder().data(dto).status(true).build();
		
	}
	
	@PostMapping("/postcomment")
	public RestResponse postComment(@RequestBody Comment comment) {
		CommentDto dto = service.postComment(comment);
		return RestResponse.builder().data(dto).status(true).build();
	}

}
