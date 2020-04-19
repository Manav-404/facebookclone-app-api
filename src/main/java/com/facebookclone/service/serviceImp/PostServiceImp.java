package com.facebookclone.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.facebookclone.dao.FriendsProcessDao;
import com.facebookclone.dao.PostDao;
import com.facebookclone.dto.PostDto;
import com.facebookclone.model.FriendsProcess;
import com.facebookclone.model.Post;
import com.facebookclone.service.PostService;

@Service
public class PostServiceImp implements PostService{
	
	@Autowired
	PostDao dao;
	
	
	FriendsProcessDao friendsDao;
	
	
	
	@Override
	public PostDto createPost(Post post) {
		// TODO Auto-generated method stub
		Post entity = dao.save(post);
		return getPostDto(entity);
	}


	@Override
	public List<PostDto> getPostOfFriends(long userId) {
		// TODO Auto-generated method stub
		List<PostDto> postList = new ArrayList<PostDto>();
		List<FriendsProcess> friendsList = friendsDao.getFriends(userId);
		for(FriendsProcess  friend : friendsList) {
			if(friend.getUser_one_id()==userId) {
				List<Post> posts = dao.getPostByUser(friend.getUser_two_id());
				posts.forEach(post->postList.add(getPostDto(post)));
			}else if(friend.getUser_two_id()==userId) {
				List<Post> posts = dao.getPostByUser(friend.getUser_one_id());
				posts.forEach(post->postList.add(getPostDto(post)));
			}
		}
		return postList;
	}

	@Override
	public List<PostDto> getWallPosts(long userId) {
		// TODO Auto-generated method stub
		List<PostDto> postList = new ArrayList<PostDto>();
		List<Post> posts = dao.getPostByUser(userId);
		posts.forEach(post->postList.add(getPostDto(post)));
		
		return postList;
	}
	
	private PostDto getPostDto(Post entity) {
		// TODO Auto-generated method stub
		return PostDto.builder().id(entity.getId()).user_id(entity.getUser().getId())
				.caption(entity.getCaption())
				.user_name(entity.getUser().getProfile().getFname()+""+entity.getUser().getProfile().getLname()).build();
	}
	
	
	

}
