package com.facebookclone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.facebookclone.model.User;


public interface UserDao extends JpaRepository<User, Long>  {
	
	@Query(value = "SELECT * FROM user where email=?1" ,nativeQuery = true)
	User getByEmail(String email);

}
