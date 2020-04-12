package com.facebookclone.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.facebookclone.dao.FriendsProcessDao;
import com.facebookclone.dao.ProfileDao;
import com.facebookclone.dto.ProfileDto;
import com.facebookclone.model.FriendsProcess;
import com.facebookclone.model.Profile;
import com.facebookclone.service.FriendsProcessService;

@Service
public class FriendsProcessServiceImp implements FriendsProcessService{
	
	@Autowired
	private FriendsProcessDao dao;
	
	@Autowired
	private ProfileDao profileDao;

	@Override
	public List<ProfileDto> getFriendsByUserId(long userId) {
		// TODO Auto-generated method stub
		List<ProfileDto> profileList = new ArrayList<ProfileDto>();								
		List<FriendsProcess> friendsList = dao.getFriends(userId);
		for(FriendsProcess friends : friendsList) {
			if(friends.getUser_one_id()==userId) {
				Profile profile = profileDao.getUserProfile(friends.getUser_two_id());
				profileList.add(getProfileDto(profile));
				
			}else if (friends.getUser_two_id()==userId) {
				Profile profile = profileDao.getUserProfile(friends.getUser_one_id());
				profileList.add(getProfileDto(profile));
			}
		}
		
		return profileList;
	}

	@Override
	public ProfileDto sendFriendRequest(long friendId, long currentUserId) {
		// TODO Auto-generated method stub
		Profile profile = null ;
		if(friendId<currentUserId) {
			FriendsProcess friend = dao.sendRequest(friendId, currentUserId, currentUserId);
			 profile  = profileDao.getUserProfile(friend.getUser_one_id());

		}else if (currentUserId<friendId) {
			FriendsProcess friend = dao.sendRequest(currentUserId, friendId, currentUserId);
			 profile  = profileDao.getUserProfile(friend.getUser_two_id());

		}
		
		return getProfileDto(profile);
	}

	@Override
	public boolean acceptFriendRequest(long friendId, long currentUserId) {
		// TODO Auto-generated method stub
		FriendsProcess friends = dao.acceptFriend(friendId, currentUserId);
		if(friends!=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean rejectFriendRequest(long friendId, long currentUserId) {
		// TODO Auto-generated method stub
		dao.deleteFriendRequest(friendId, currentUserId);
		return true;
	}

	@Override
	public List<ProfileDto> toAccept(long currentUserId) {
		// TODO Auto-generated method stub
		List<ProfileDto> profileList= new ArrayList<>();
		List<FriendsProcess> friendsList = dao.getToBeAcceptedList(currentUserId);
		for(FriendsProcess friend:friendsList) {
			if(friend.getUser_one_id()==currentUserId) {
				Profile profile = profileDao.getUserProfile(friend.getUser_one_id());
				profileList.add(getProfileDto(profile));
			}else if (friend.getUser_two_id()==currentUserId){
				Profile profile = profileDao.getUserProfile(friend.getUser_two_id());
				profileList.add(getProfileDto(profile));

			}
		}
		return profileList;
	}
	
	private ProfileDto getProfileDto(Profile profile) {
		return ProfileDto.builder().fname(profile.getFname()).lname(profile.getLname())
				.city(profile.getCity()).id(profile.getId()).user_id(profile.getUser().getId()).build();
	}

}
