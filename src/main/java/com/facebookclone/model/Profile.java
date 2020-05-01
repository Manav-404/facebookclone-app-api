package com.facebookclone.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String fname;
	
	private String lname;
	
	private String city;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "u_id" , nullable = false)
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY ,mappedBy = "profile")
	private List<Comment> comment;

	
	public Profile(){}


	public Profile(long id, String fname, String lname, String city, User user) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.city = city;
		this.user = user;
	}
	
	
	

}
