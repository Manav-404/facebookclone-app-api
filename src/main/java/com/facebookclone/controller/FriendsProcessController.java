package com.facebookclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebookclone.dto.ProfileDto;
import com.facebookclone.service.FriendsProcessService;
import com.facebookclone.utils.RestResponse;

@RestController
@RequestMapping("/api/friends")
public class FriendsProcessController {

	@Autowired
	FriendsProcessService service;
	
	@PostMapping("/send/{friendId}/{userId}")
	public RestResponse sendFriendRequest(@PathVariable("friendId")Long friendId, @PathVariable("userId")Long userId) {
		ProfileDto dto  = service.sendFriendRequest(friendId, userId);
		return RestResponse.builder().data(dto).status(true).build();
		
	}
	
	
}
