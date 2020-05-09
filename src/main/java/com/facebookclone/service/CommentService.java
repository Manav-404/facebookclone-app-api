package com.facebookclone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facebookclone.dto.CommentDto;
import com.facebookclone.model.Comment;

public interface CommentService {
	CommentDto postComment(Comment comment);
}
