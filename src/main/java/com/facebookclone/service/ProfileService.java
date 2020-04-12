package com.facebookclone.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.facebookclone.dto.ProfileDto;
import com.facebookclone.model.Profile;


public interface ProfileService {
	
	ProfileDto getProfileByUser(long userId);
	boolean createProfile (Profile profile ,MultipartFile file);
	ProfileDto editProfile(Profile profile);
}
