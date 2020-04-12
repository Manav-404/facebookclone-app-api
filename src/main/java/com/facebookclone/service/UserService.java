package com.facebookclone.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.facebookclone.dto.UserDto;
import com.facebookclone.model.User;


public interface UserService {

	UserDto getUserById(long userId);
	boolean deleteUserById(long userId);
	UserDto createUser(User user);
		UserDto getUserByEmail(String email);
}
