package com.facebookclone.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY , mappedBy = "user" )
	private List<Post> posts;
	
	@OneToOne(cascade = CascadeType.ALL ,fetch = FetchType.LAZY , mappedBy = "user")
	private Profile profile;
	
	public User() {}

	public User(long id, String email, String password, List<Post> posts, Profile profile) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.posts = posts;
		this.profile = profile;
	}
	
	

	
	
	

}
