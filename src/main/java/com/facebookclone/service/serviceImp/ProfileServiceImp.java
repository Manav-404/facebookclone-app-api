package com.facebookclone.service.serviceImp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.facebookclone.dao.FriendsProcessDao;
import com.facebookclone.dao.ProfileDao;
import com.facebookclone.dao.UserDao;
import com.facebookclone.dto.ProfileDto;
import com.facebookclone.model.FriendsProcess;
import com.facebookclone.model.Profile;
import com.facebookclone.model.User;
import com.facebookclone.service.ProfileService;
import com.facebookclone.utils.JwtUtils;

@Service
public class ProfileServiceImp implements ProfileService {
	
	String httpServer = "http://127.0.0.1:9090/";
	
	@Autowired
	private ProfileDao dao ;
	
	@Autowired
	private JwtUtils tokenUtils;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private org.springframework.core.env.Environment env;
	
	@Autowired
	private FriendsProcessDao friendsDao;

	@Override
	public ProfileDto getProfileByUser(long userId) throws Exception {
		// TODO Auto-generated method stub
		String path = env.getProperty("doc.profile")+userId;
		String new_path="";
		File files = new File(path);
			File[] fileList = files.listFiles();
			for(File file : fileList) {
				 if(file.exists()) {
					 new_path = path+"\\"+file.getName();
				 }
			}
			
			String returnPath = httpServer+new_path.substring(16);
		
		Profile profile = dao.getUserProfile(userId);
		if(profile==null) {
			throw new Exception("Profile setup needed");
		}
		return getProfileDto(profile , returnPath);
	}

	@Override
	public ProfileDto createProfile(Profile profile ,String token) throws Exception {
		
		try {
			if(profile==null) {
				throw new Exception("Please try again.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String email = tokenUtils.extractUsername(token.substring(7));
		User user = userDao.getByEmail(email);
		profile.setUser(user);

		Profile prof = dao.save(profile);
		 return ProfileDto.builder().fname(profile.getFname()).lname(profile.getLname())
				.city(profile.getCity()).id(profile.getId()).user_id(profile.getUser().getId()).build();
		

		
	}
	
	@Override
	public String uploadProfilePhoto(MultipartFile file , long id , String token) throws Exception {
		// TODO Auto-generated method stub
		String email = tokenUtils.extractUsername(token.substring(7));
		User user = userDao.getOne(id);
		if(!user.getEmail().equalsIgnoreCase(email)) {
			throw new Exception("You are not authorized to change the profile picture");
		}
		
		
		String path = env.getProperty("doc.profile") +id+"\\";
		File dir = new File(path);
		dir.mkdirs();
		
		File[] contents = dir.listFiles();
		for(File f : contents) {
			if(f.exists()) {
				f.delete();
			}
		}
		String updatedFileName = file.getOriginalFilename().replace("fakepath", "Users\\Hooman");
		
		File uploadedFile = new File(dir, updatedFileName);
		
		try {
			if(!uploadedFile.exists()) {
				file.transferTo(uploadedFile);
			}
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return httpServer+ uploadedFile.getPath().substring(16);

		
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

	@Override
	public List<ProfileDto> getBySearch(String name , String token) {
		// TODO Auto-generated method stub
		String email = tokenUtils.extractUsername(token.substring(7));
		User user = userDao.getByEmail(email);
		long currentUserId = user.getId();
		List<ProfileDto> dtoList = new ArrayList<ProfileDto>();
		List<Profile> prof = dao.searchByName(name);
		List<FriendsProcess> pending = friendsDao.getPendingRequest(currentUserId);
		List<FriendsProcess> friends = friendsDao.getFriends(currentUserId);
		List<FriendsProcess> filterList = new ArrayList<>();
		filterList.addAll(pending);
		filterList.addAll(friends);
		
		for(Profile searchProfile : prof) {
			String path = env.getProperty("doc.profile")+searchProfile.getUser().getId();
			String new_path="";
			File files = new File(path);
				File[] fileList = files.listFiles();
				for(File file : fileList) {
					 if(file.exists()) {
						 new_path = path+"\\"+file.getName();
					 }
				}
				String returnPath = httpServer+new_path.substring(16);
				dtoList.add(getProfileDto(searchProfile, returnPath));
				
				
		}
		
		for(int i =0 ; i<filterList.size();i++) {
			for(int j = 0 ; j<dtoList.size();j++) {
				if(filterList.get(i).getUser_action()==currentUserId) {
					if(filterList.get(i).getUser_one_id()==currentUserId) {
						if(dtoList.get(j).getUser_id()==filterList.get(i).getUser_two_id()||dtoList.get(j).getUser_id()==filterList.get(i).getUser_action()) {
							dtoList.remove(j);
						}
					}else if(filterList.get(i).getUser_two_id()==currentUserId) {
						if(dtoList.get(j).getUser_id()==filterList.get(i).getUser_one_id()||dtoList.get(j).getUser_id()==filterList.get(i).getUser_action()) {
							dtoList.remove(j);
							break;

						}
					}
				}else {
					if(filterList.get(i).getUser_one_id()==currentUserId||filterList.get(i).getUser_two_id()==currentUserId) {
						if(dtoList.get(j).getUser_id()==filterList.get(i).getUser_action()||dtoList.get(j).getUser_id()==currentUserId||
								dtoList.get(j).getUser_id()==currentUserId) {
							dtoList.remove(j);
							break;
						}
					}
				}
			}
			
			
		}


		
			
			return dtoList ;
		
		
	}

	
}
