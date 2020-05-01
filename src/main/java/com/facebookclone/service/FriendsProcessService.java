package com.facebookclone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facebookclone.dto.ProfileDto;


public interface FriendsProcessService {
	
	List<ProfileDto> getFriendsByUserId(long userId);
	ProfileDto sendFriendRequest(long friendId, long currentUserId);
	boolean acceptFriendRequest(long friendId , long currentUserId);
	boolean rejectFriendRequest(long friendId , long currentUserId);
	List<ProfileDto> toAccept(long currentUserId) throws Exception;
	

}
