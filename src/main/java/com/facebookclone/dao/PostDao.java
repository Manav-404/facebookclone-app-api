package com.facebookclone.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.facebookclone.model.Post;


public interface PostDao extends JpaRepository<Post, Long> {
	
	@Query(value = "SELECT * FROM post where u_id = ?1" ,nativeQuery = true)
	List<Post> getPostByUser(long userId);

}
