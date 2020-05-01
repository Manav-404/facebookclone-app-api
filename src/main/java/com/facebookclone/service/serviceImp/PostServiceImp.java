package com.facebookclone.service.serviceImp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.facebookclone.dao.FriendsProcessDao;
import com.facebookclone.dao.PostDao;
import com.facebookclone.dto.PostDto;
import com.facebookclone.model.FriendsProcess;
import com.facebookclone.model.Post;
import com.facebookclone.service.PostService;

@Service
public class PostServiceImp implements PostService{
	
	
	private FriendsProcessDao friendsDao;
	
	
	@Autowired
	private PostDao dao;

	
	@Autowired
	private Environment env;
	
	
	@Override
	public PostDto createPost(Post post , MultipartFile file) {
		// TODO Auto-generated method stub
		Post entity = dao.save(post);
		String path = env.getProperty("doc.profile") +post.getUser().getId()+"\\"+post.getId()+"\\";
		File dir = new File(path);
		dir.mkdirs();
		File uploadedFile = new File(dir, file.getOriginalFilename());
		
		try {
			file.transferTo(uploadedFile);
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String new_path = path+file.getOriginalFilename();
		return getPostDto(entity ,new_path );
	}


	@Override
	public List<PostDto> getPostOfFriends(long userId) {
		// TODO Auto-generated method stub
		List<PostDto> postList = new ArrayList<PostDto>();
		List<FriendsProcess> friendsList = friendsDao.getFriends(userId);
		for(FriendsProcess  friend : friendsList) {
			if(friend.getUser_one_id()==userId) {
				List<Post> posts = dao.getPostByUser(friend.getUser_two_id());
				for(Post post:posts) {
					String path = getPostPicture(post);
					postList.add(getPostDto(post , path));
				}
			}else if(friend.getUser_two_id()==userId) {
				List<Post> posts = dao.getPostByUser(friend.getUser_one_id());
				for(Post post:posts) {
					String path = getPostPicture(post);
					postList.add(getPostDto(post , path));
				}
			}
		}
		return postList;
	}
	
	private String getPostPicture(Post post) {
		String path = env.getProperty("doc.post")+post.getUser().getId()+"\\"+post.getId()+"\\";
		String new_path="";
		File files = new File(path);
			File[] fileList = files.listFiles();
			for(File file : fileList) {
				 if(file.exists()) {
					 new_path = path+"\\"+file.getName();
				 }
			}
			
			return new_path;
	}

	@Override
	public List<PostDto> getWallPosts(long userId) {
		// TODO Auto-generated method stub
		List<PostDto> postList = new ArrayList<PostDto>();
		List<Post> posts = dao.getPostByUser(userId);
		for(Post post:posts) {
			String path = getPostPicture(post);
			postList.add(getPostDto(post , path));
		}
		
		return postList;
	}
	
	private PostDto getPostDto(Post entity , String path) {
		// TODO Auto-generated method stub
		return PostDto.builder().id(entity.getId()).user_id(entity.getUser().getId())
				.caption(entity.getCaption())
				.user_name(entity.getUser().getProfile().getFname()+""+entity.getUser().getProfile().getLname()).path(path).build();
	}
	
	
	

}
