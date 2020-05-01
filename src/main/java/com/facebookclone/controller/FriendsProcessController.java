package com.facebookclone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PutMapping("/accept/{friendId}/{userId}")
	public RestResponse acceptFriendRequest(@PathVariable("friendId")Long friendId, @PathVariable("userId")Long userId) {
		boolean accepted = service.acceptFriendRequest(friendId, userId);
		if(accepted) {
			return RestResponse.builder().data(accepted).status(true).build();
		}else {
			return RestResponse.builder().status(false).build();
		}
	}
	
	@GetMapping("/list/{userId}")
	public RestResponse getFriends(@PathVariable("userId")Long userId) {
		List<ProfileDto> dto = service.getFriendsByUserId(userId);
		return RestResponse.builder().data(dto).status(true).build();
	}
	
	@PostMapping("/reject/{friendId}/{userId}")
	public RestResponse rejectFriendRequest(@PathVariable("friendId")Long friendId, @PathVariable("userId")Long userId) {
		boolean rejected = service.rejectFriendRequest(friendId, userId);
		if(rejected) {
			return RestResponse.builder().data(rejected).status(true).build();
		}else {
			return RestResponse.builder().status(false).build();
		}
	}
	
	@GetMapping("/pending/{userId}")
	public RestResponse pendingRequests(@PathVariable("userId")Long userId) throws Exception {
		List<ProfileDto> dto =  service.toAccept(userId);
		return RestResponse.builder().data(dto).status(true).build();
	}
	
	
	
	
}
