package com.facebookclone.service.serviceImp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.facebookclone.dao.ProfileDao;
import com.facebookclone.dao.UserDao;
import com.facebookclone.dto.ProfileDto;
import com.facebookclone.model.Profile;
import com.facebookclone.model.User;
import com.facebookclone.service.ProfileService;
import com.facebookclone.utils.JwtUtils;

@Service
public class ProfileServiceImp implements ProfileService {
	
	@Autowired
	private ProfileDao dao ;
	
	@Autowired
	private JwtUtils tokenUtils;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private org.springframework.core.env.Environment env;

	@Override
	public ProfileDto getProfileByUser(long userId) {
		// TODO Auto-generated method stub
		String path = env.getProperty("doc.profile")+userId;
		String new_path="";
		File files = new File(path);
		System.out.println(files.getAbsolutePath());
			File[] fileList = files.listFiles();
			for(File file : fileList) {
				 if(file.exists()) {
					 new_path = path+"\\"+file.getName();
					 System.out.println(new_path);
				 }
			}
		
		Profile profile = dao.getUserProfile(userId);
		return getProfileDto(profile , new_path);
	}

	@Override
	public ProfileDto createProfile(Profile profile ,String token) {
		
		String email = tokenUtils.extractUsername(token.substring(7));
		User user = userDao.getByEmail(email);
		profile.setUser(user);

		Profile prof = dao.save(profile);
		 return ProfileDto.builder().fname(profile.getFname()).lname(profile.getLname())
				.city(profile.getCity()).id(profile.getId()).user_id(profile.getUser().getId()).build();
		

		
	}
	
	@Override
	public String uploadProfilePhoto(MultipartFile file , long id) {
		// TODO Auto-generated method stub
		String path = env.getProperty("doc.profile") +id+"\\";
		File dir = new File(path);
		dir.mkdirs();
		
		File[] contents = dir.listFiles();
		for(File f : contents) {
			if(f.exists()) {
				f.delete();
			}
		}
		
		File uploadedFile = new File(dir, file.getOriginalFilename());
		
		try {
			if(!uploadedFile.exists()) {
				file.transferTo(uploadedFile);
			}
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return uploadedFile.getPath();

		
	}
	
	


	@Override
	public ProfileDto editProfile(Profile profile , long id) {
		// TODO Auto-generated method stub
		Profile p = dao.getOne(id);
		Profile prof = dao.saveAndFlush(p);
		return ProfileDto.builder().fname(prof.getFname()).lname(prof.getLname())
				.city(prof.getCity()).id(prof.getId()).user_id(prof.getUser().getId()).build();
	}
	
	private ProfileDto getProfileDto(Profile profile , String path) {
		return ProfileDto.builder().fname(profile.getFname()).lname(profile.getLname())
				.city(profile.getCity()).id(profile.getId()).user_id(profile.getUser().getId()).imagePath(path).build();
	}

	
}
