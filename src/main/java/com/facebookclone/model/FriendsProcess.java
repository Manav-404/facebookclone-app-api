package com.facebookclone.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FriendsProcess implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long user_one_id;
	
	
	private long user_two_id;
	
	//0-Pending request , 1- request Accepted , on decline delete the entry from database
	@ColumnDefault("0")
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
