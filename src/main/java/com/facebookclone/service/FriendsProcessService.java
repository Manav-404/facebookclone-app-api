package com.facebookclone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facebookclone.dto.ProfileDto;


public interface FriendsProcessService {
	
	List<ProfileDto> getFriendsByUserId(String token);
	ProfileDto sendFriendRequest(long friendId,String token);
	boolean acceptFriendRequest(long friendId , String token) throws Exception;
	boolean rejectFriendRequest(long friendId , String token);
	List<ProfileDto> toAccept(String token) throws Exception;
	

}
