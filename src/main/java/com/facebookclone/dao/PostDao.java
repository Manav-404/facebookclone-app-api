package com.facebookclone.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.facebookclone.model.Post;

@Repository
public interface PostDao extends CrudRepository<Post, Long> {

}
