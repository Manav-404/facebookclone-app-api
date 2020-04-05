package com.facebookclone.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.facebookclone.model.Comment;

@Repository
public interface CommentDao extends CrudRepository<Comment,Long> {

}
