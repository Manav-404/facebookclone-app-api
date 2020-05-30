package com.facebookclone.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.facebookclone.dao.CommentDao;
import com.facebookclone.dto.CommentDto;
import com.facebookclone.dto.ProfileDto;
import com.facebookclone.model.Comment;
import com.facebookclone.model.Profile;
import com.facebookclone.service.CommentService;

@Service
public class CommentServiceImp implements CommentService {
	
	@Autowired
	private CommentDao dao;

//	@Override
//	public List<CommentDto> getCommentByPostId(List<Long> postId) {
//		// TODO Auto-generated method stub
//		List<CommentDto> commentList = new ArrayList<>();
//		for(int i =0 ; i<postId.size() ; i++) {
//			List<Comment> list = dao.getByPost(postId.get(i));
//			for(Comment comment : list) {
//				commentList.add(getCommentDto(comment));
//			}
//		}
//		
//		return commentList;
//	}

	@Override
	public CommentDto postComment(Comment comment) {
		// TODO Auto-generated method stub
		Comment com = dao.save(comment);
		return getCommentDto(com);
	}
	
	
	
	private CommentDto getCommentDto(Comment comment) {
		return CommentDto.builder().id(comment.getId()).text(comment.getText()).profile(getProfileDto(comment.getProfile()))
				.build();
	}
	
	private ProfileDto getProfileDto(Profile profile) {
		return ProfileDto.builder().fname(profile.getFname()).lname(profile.getLname())
				.city(profile.getCity()).id(profile.getId()).user_id(profile.getUser().getId()).build();
	}

}
