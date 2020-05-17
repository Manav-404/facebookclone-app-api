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

import com.facebookclone.dao.CommentDao;
import com.facebookclone.dao.FriendsProcessDao;
import com.facebookclone.dao.PostDao;
import com.facebookclone.dao.UserDao;
import com.facebookclone.dto.CommentDto;
import com.facebookclone.dto.PostDto;
import com.facebookclone.dto.ProfileDto;
import com.facebookclone.model.Comment;
import com.facebookclone.model.FriendsProcess;
import com.facebookclone.model.Post;
import com.facebookclone.model.Profile;
import com.facebookclone.model.User;
import com.facebookclone.service.PostService;
import com.facebookclone.utils.JwtUtils;

@Service
public class PostServiceImp implements PostService{
	
	String httpServer = "http://127.0.0.1:9090/";
	

	@Autowired
	private FriendsProcessDao friendsDao;
	
	
	@Autowired
	private PostDao dao;
	
	@Autowired
	private CommentDao commentDao ;
	
	@Autowired
	private JwtUtils utils;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private Environment env;
	
	
	@Override
	public PostDto createPost(String text ,String token) {
		// TODO Auto-generated method stub
		String username = utils.extractUsername(token.substring(7));
		User user = userDao.getByEmail(username);
		Post post = new Post();
		post.setCaption(text);
		post.setUser(user);
		Post entity = dao.save(post);
		return PostDto.builder().id(entity.getId()).user_id(entity.getUser().getId())
				.caption(entity.getCaption())
				.user_name(entity.getUser().getProfile().getFname()+""+entity.getUser().getProfile().getLname()).build();
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
					List<Comment> comments = commentDao.getByPost(post.getId());
					post.setComments(comments);
					String path = getPostPicture(post);
					String profile = getPostProfilePicture(post);
					postList.add(getPostDto(post , path , profile));
				}
			}else if(friend.getUser_two_id()==userId) {
				List<Post> posts = dao.getPostByUser(friend.getUser_one_id());
				for(Post post:posts) {
					List<Comment> comments = commentDao.getByPost(post.getId());
					post.setComments(comments);
					String path = getPostPicture(post);
					String profile = getPostProfilePicture(post);
					postList.add(getPostDto(post , path , profile));
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
			String replacedPath = httpServer+new_path.substring(16);
			
			return replacedPath;
	}
	
	private String getPostProfilePicture(Post post) {
		System.out.println(post.getUser().getId());
		String path = env.getProperty("doc.profile")+post.getUser().getId();
		String new_path="";
		File files = new File(path);
			File[] fileList = files.listFiles();
			for(File file : fileList) {
				 if(file.exists()) {
					 new_path = path+"\\"+file.getName();
				 }
			}
			String returnPath = httpServer+new_path.substring(16);
			
			return returnPath;
	}
	
	

	@Override
	public List<PostDto> getWallPosts(long userId) {
		// TODO Auto-generated method stub
		List<PostDto> postList = new ArrayList<PostDto>();
		List<Post> posts = dao.getPostByUser(userId);
		for(Post post:posts) {
			List<Comment> comments = commentDao.getByPost(post.getId());
			post.setComments(comments);
			String path = getPostPicture(post);
			String profile = getPostProfilePicture(post);
			postList.add(getPostDto(post , path , profile));
		}
		
		return postList;
	}
	

	@Override
	public String postPic(MultipartFile file, long postId) {
		// TODO Auto-generated method stub
		Post entity = dao.getOne(postId);
		String path = env.getProperty("doc.post") +entity.getUser().getId()+"\\"+entity.getId()+"\\";
		File dir = new File(path);
		dir.mkdirs();
		String replacedPath = file.getOriginalFilename().replace("fakepath", "Users\\Hooman");
		File uploadedFile = new File(dir, replacedPath);
		
		try {
			file.transferTo(uploadedFile);
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String new_path = httpServer+path.substring(16)+file.getOriginalFilename();
		return new_path;
	}
	
	private ProfileDto getProfileDto(Profile profile) {
		return ProfileDto.builder().fname(profile.getFname()).lname(profile.getLname())
				.city(profile.getCity()).id(profile.getId()).user_id(profile.getUser().getId()).build();
	}
	
	private CommentDto getCommentDto(Comment comment) {
		return CommentDto.builder().id(comment.getId()).text(comment.getText()).profile(getProfileDto(comment.getProfile()))
				.build();
	}
	
	private PostDto getPostDto(Post entity , String path , String profile) {
		// TODO Auto-generated method stub
		List<CommentDto> comDto = new ArrayList<>();
		List<Comment> comments = entity.getComments();
		for(Comment com : comments) {
			CommentDto dto = getCommentDto(com);
			comDto.add(dto);
		}
		return PostDto.builder().id(entity.getId()).user_id(entity.getUser().getId())
				.caption(entity.getCaption()).profile(profile)
				.user_name(entity.getUser().getProfile().getFname()+""+entity.getUser().getProfile().getLname()).comments(comDto).path(path).build();
	}


	
	
	

}
