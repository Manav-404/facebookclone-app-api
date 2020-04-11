package com.facebookclone.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.facebookclone.dto.CommentDto;
import com.facebookclone.model.Comment;

@Repository
public interface CommentDao extends JpaRepository<Comment,Long> {

	@Query(value = "SELECT * FROM comment where p_id=?1" , nativeQuery = true)
	List<Comment> getByPost(long postId);
}
