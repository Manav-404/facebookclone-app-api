package com.facebookclone.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "p_id" , nullable = false)
	private Post post;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prof_id" , nullable = false)
	private Profile profile;
	
	
	private Date createdDate;

	public Comment() {}
	
	@PrePersist
	public void getCreatedDate() {
		createdDate  = new Date();
	}
	
	public Comment(long id, String text, Post post , Profile profile) {
		this.id = id;
		this.text = text;
		this.post = post;
		this.profile = profile;
	}
	
	

}
