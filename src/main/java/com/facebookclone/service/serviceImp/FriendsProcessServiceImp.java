package com.facebookclone.service.serviceImp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.facebookclone.dao.FriendsProcessDao;
import com.facebookclone.dao.ProfileDao;
import com.facebookclone.dao.UserDao;
import com.facebookclone.dto.ProfileDto;
import com.facebookclone.model.FriendsProcess;
import com.facebookclone.model.Profile;
import com.facebookclone.model.User;
import com.facebookclone.service.FriendsProcessService;
import com.facebookclone.utils.JwtUtils;

@Service
public class FriendsProcessServiceImp implements FriendsProcessService{
	
	String httpServer = "http://127.0.0.1:9090/";

	
	@Autowired
	private FriendsProcessDao dao;
	
	@Autowired
	private ProfileDao profileDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JwtUtils authUtils;
	
	@Autowired
	private org.springframework.core.env.Environment env;


	@Override
	public List<ProfileDto> getFriendsByUserId(String token) {
		// TODO Auto-generated method stub
		token=token.substring(7);
		String username = authUtils.extractUsername(token);
		User user = userDao.getByEmail(username);
		long userId = user.getId();
		List<ProfileDto> profileList = new ArrayList<ProfileDto>();								
		List<FriendsProcess> friendsList = dao.getFriends(userId);
		for(FriendsProcess friends : friendsList) {
			if(friends.getUser_one_id()==userId) {
				Profile profile = profileDao.getUserProfile(friends.getUser_two_id());
				String path = getPath(profile);
				profileList.add(getProfileDto(profile , path));
				
			}else if (friends.getUser_two_id()==userId) {
				Profile profile = profileDao.getUserProfile(friends.getUser_one_id());
				String path = getPath(profile);
				profileList.add(getProfileDto(profile ,path));
			}
		}
		
		return profileList;
	}

	@Override
	public ProfileDto sendFriendRequest(long friendId, String token) {
		// TODO Auto-generated method stub
		token=token.substring(7);
		String username = authUtils.extractUsername(token);
		User user = userDao.getByEmail(username);
		long currentUserId = user.getId();
		Profile profile = null ;
		if(friendId<currentUserId) {
			FriendsProcess f = new FriendsProcess();
			f.setUser_one_id(friendId);
			f.setUser_two_id(currentUserId);
			f.setUser_action(currentUserId);
			FriendsProcess friend = dao.save(f);
			 profile  = profileDao.getUserProfile(friend.getUser_one_id());

		}else if (currentUserId<friendId) {
			FriendsProcess f = new FriendsProcess();
			f.setUser_one_id(currentUserId);
			f.setUser_two_id(friendId);
			f.setUser_action(currentUserId);
			FriendsProcess friend = dao.save(f);			 
			profile  = profileDao.getUserProfile(friend.getUser_two_id());

		}
		String path = getPath(profile);
		
		return getProfileDto(profile ,path);
	}

	@Override
	public boolean acceptFriendRequest(long friendId, String token) throws Exception {
		// TODO Auto-generated method stub
		token=token.substring(7);
		String username = authUtils.extractUsername(token);
		User user = userDao.getByEmail(username);
		long currentUserId = user.getId();
		int friends = dao.acceptFriend(friendId, currentUserId);
		if(friends>0) {
			return true;
		}else {
			throw new Exception("Could not process request . Please try again.");
		}
		
	}

	@Override
	public boolean rejectFriendRequest(long friendId, String token) {
		// TODO Auto-generated method stub
		token=token.substring(7);
		String username = authUtils.extractUsername(token);
		User user = userDao.getByEmail(username);
		long currentUserId = user.getId();
		int delete = dao.deleteFriendRequest(friendId, currentUserId);
		if(delete>0) {
			return true;
		}
		return false;
	}

	@Override
	public List<ProfileDto> toAccept(String token) throws Exception {
		// TODO Auto-generated method stub
		token=token.substring(7);
		String username = authUtils.extractUsername(token);
		User user = userDao.getByEmail(username);
		long currentUserId = user.getId();
		List<ProfileDto> profileList= new ArrayList<>();
		List<FriendsProcess> friendsList = dao.getToBeAcceptedList(currentUserId);
		if(friendsList==null) {
			throw new Exception("You do not have any friend request to be accepted");
		}
		for(FriendsProcess friend:friendsList) {
			if(friend.getUser_one_id()==currentUserId&&friend.getUser_action()!=currentUserId) {
				Profile profile = profileDao.getUserProfile(friend.getUser_two_id());
				String path = getPath(profile);
				profileList.add(getProfileDto(profile , path));
			}else if (friend.getUser_two_id()==currentUserId&&friend.getUser_action()!=currentUserId){
				Profile profile = profileDao.getUserProfile(friend.getUser_one_id());
				String path = getPath(profile);
				profileList.add(getProfileDto(profile ,path));

			}
		}
		return profileList;
	}
	
	private String getPath(Profile profile) {
		String path = env.getProperty("doc.profile")+profile.getUser().getId();
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
	
	private ProfileDto getProfileDto(Profile profile , String path) {
		return ProfileDto.builder().fname(profile.getFname()).lname(profile.getLname())
				.city(profile.getCity()).id(profile.getId()).user_id(profile.getUser().getId()).imagePath(path).build();
	}

}
