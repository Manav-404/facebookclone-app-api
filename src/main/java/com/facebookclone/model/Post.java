package com.facebookclone.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String caption;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "u_id" , nullable = false)
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY ,mappedBy = "post")
	private List<Comment> comments;
	
	
	public Post() {}


	public Post(long id, String caption, User user, List<Comment> comments) {
		super();
		this.id = id;
		this.caption = caption;
		this.user = user;
		this.comments = comments;
	};
	
	
	
	


}
