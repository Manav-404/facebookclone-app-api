package com.facebookclone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
	
	@GetMapping("/send/{friendId}")
	public RestResponse sendFriendRequest(@PathVariable("friendId")Long friendId, @RequestHeader("Authorization")String token) {
		ProfileDto dto  = service.sendFriendRequest(friendId, token);
		return RestResponse.builder().data(dto).status(true).build();
		
	}
	
	@GetMapping("/accept/{friendId}")
	public RestResponse acceptFriendRequest(@PathVariable("friendId")Long friendId, @RequestHeader("Authorization")String token) throws Exception {
		boolean accepted = service.acceptFriendRequest(friendId, token);
		if(accepted) {
			return RestResponse.builder().data(accepted).status(true).build();
		}else {
			return RestResponse.builder().status(false).build();
		}
	}
	
	@GetMapping("/list")
	public RestResponse getFriends(@RequestHeader("Authorization")String token) {
		List<ProfileDto> dto = service.getFriendsByUserId(token);
		return RestResponse.builder().data(dto).status(true).build();
	}
	
	@GetMapping("/reject/{friendId}")
	public RestResponse rejectFriendRequest(@PathVariable("friendId")Long friendId, @RequestHeader("Authorization")String token) {
		boolean rejected = service.rejectFriendRequest(friendId, token);
		if(rejected) {
			return RestResponse.builder().data(rejected).status(true).build();
		}else {
			return RestResponse.builder().status(false).build();
		}
	}
	
	@GetMapping("/pending")
	public RestResponse pendingRequests(@RequestHeader("Authorization")String token) throws Exception {
		List<ProfileDto> dto =  service.toAccept(token);
		return RestResponse.builder().data(dto).status(true).build();
	}
	
	
	
	
}
