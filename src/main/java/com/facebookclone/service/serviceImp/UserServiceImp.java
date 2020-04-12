package com.facebookclone.service.serviceImp;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.facebookclone.dao.UserDao;
import com.facebookclone.dto.UserDto;
import com.facebookclone.model.User;
import com.facebookclone.service.UserService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserDao dao;
	
	@Override
	public UserDto getUserById(long userId) {
		// TODO Auto-generated method stub
		Optional<User> user = dao.findById(userId);
		return UserDto.builder().email(user.get().getEmail()).id(user.get().getId())
				.build();
	}

	@Override
	public boolean deleteUserById(long userId) {
		// TODO Auto-generated method stub
		 dao.deleteById(userId);
		return true;
	}

	@Override
	public UserDto createUser(User user) {
		// TODO Auto-generated method stub
		User entity = dao.save(user);
		return getUserDto(entity);
	}
	
	@Override
	public UserDto getUserByEmail(String email) {
		// TODO Auto-generated method stub
		User user = dao.getByEmail(email);
		return getUserDto(user);
	}
	
	@SuppressWarnings("unused")
	private UserDto getUserDto(User entity) {
		return UserDto.builder().id(entity.getId()).email(entity.getEmail())
		.build();
		
	}

	

}
