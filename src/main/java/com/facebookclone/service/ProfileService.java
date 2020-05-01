package com.facebookclone.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.facebookclone.dto.ProfileDto;
import com.facebookclone.model.Profile;


public interface ProfileService {
	
	ProfileDto getProfileByUser(long userId);
	ProfileDto createProfile (Profile profile , String token) throws Exception;
	String uploadProfilePhoto(MultipartFile file , long id , String token)  throws Exception;
	ProfileDto editProfile(Profile profile ,long id);
	ProfileDto getBySearch(String name);
}
