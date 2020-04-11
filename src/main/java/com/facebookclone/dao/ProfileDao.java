package com.facebookclone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.facebookclone.model.Profile;

@Repository
public interface ProfileDao extends JpaRepository<Profile, Long> {

	@Query(value = "SELECT * FROM profile where u_id=?1")
	Profile getUserProfile(long userId);
	
}
