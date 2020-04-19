package com.facebookclone.service.serviceImp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.facebookclone.dao.ProfileDao;
import com.facebookclone.dto.ProfileDto;
import com.facebookclone.model.Profile;
import com.facebookclone.service.ProfileService;

@Service
public class ProfileServiceImp implements ProfileService {
	
	@Autowired
	ProfileDao dao ;
	
	@Value("doc.profile")
	String docPath;

	@Override
	public ProfileDto getProfileByUser(long userId) {
		// TODO Auto-generated method stub
		String path = docPath+"/"+userId+"/";
		String new_path="";
		File files = new File(path);
		File[] fileList = files.listFiles();
		for(File file : fileList) {
			 new_path = path+""+file.getName();
		}
		Profile profile = dao.getUserProfile(userId);
		return getProfileDto(profile , new_path);
	}

	@Override
	public boolean createProfile(Profile profile ,MultipartFile file) {
		// TODO Auto-generated method stub
		String path = docPath+"/"+profile.getUser().getId()+"/"+file.getOriginalFilename();
		File picture = new File(path);
		try {
			file.transferTo(picture);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Profile prof = dao.save(profile);
		if(prof!=null) {
			return true;
		}
		return false;
		
	}

	@Override
	public ProfileDto editProfile(Profile profile) {
		// TODO Auto-generated method stub
		Profile prof = dao.saveAndFlush(profile);
		return ProfileDto.builder().fname(profile.getFname()).lname(profile.getLname())
				.city(profile.getCity()).id(profile.getId()).user_id(profile.getUser().getId()).build();
	}
	
	private ProfileDto getProfileDto(Profile profile , String path) {
		return ProfileDto.builder().fname(profile.getFname()).lname(profile.getLname())
				.city(profile.getCity()).id(profile.getId()).user_id(profile.getUser().getId()).imagePath(path).build();
	}
	
	

}
