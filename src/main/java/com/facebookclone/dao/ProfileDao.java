package com.facebookclone.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.facebookclone.model.Profile;

@Repository
public interface ProfileDao extends CrudRepository<Profile, Long> {

}
