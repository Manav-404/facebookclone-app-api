package com.facebookclone.model;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendsProcess {
	
	private long user_one_id;
	private long user_two_id;
	
	//0-Pending request , 1- request Accepted , on decline delete the entry from database
	private long status;
	private long user_action;
	
	public FriendsProcess() {}

	public FriendsProcess(long user_one_id, long user_two_id, long status, long user_action) {
		this.user_one_id = user_one_id;
		this.user_two_id = user_two_id;
		this.status = status;
		this.user_action = user_action;
	}

	

}
