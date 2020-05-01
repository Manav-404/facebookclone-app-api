package com.facebookclone.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.facebookclone.dao.CommentDao;
import com.facebookclone.dto.CommentDto;
import com.facebookclone.model.Comment;
import com.facebookclone.service.CommentService;

@Service
public class CommentServiceImp implements CommentService {
	
	@Autowired
	private CommentDao dao;

	@Override
	public List<CommentDto> getCommentByPostId(long postId) {
		// TODO Auto-generated method stub
		List<CommentDto> commentList = new ArrayList<>();
		List<Comment> list = dao.getByPost(postId);
		for(Comment comment : list) {
			commentList.add(getCommentDto(comment));
		}
		return commentList;
	}

	@Override
	public CommentDto postComment(Comment comment) {
		// TODO Auto-generated method stub
		Comment com = dao.save(comment);
		return getCommentDto(com);
	}
	
	
	private CommentDto getCommentDto(Comment comment) {
		return CommentDto.builder().id(comment.getId()).text(comment.getText()).profile(comment.getProfile())
				.build();
	}

}
