package com.facebookclone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facebookclone.dto.CommentDto;
import com.facebookclone.model.Comment;

@Service
public interface CommentService {

	List<CommentDto> getCommentByPostId(long postId);
	CommentDto postComment(Comment comment);
}
