package com.facebookclone.service.serviceImp;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.facebookclone.dao.UserDao;
import com.facebookclone.model.User;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	
	private UserDao dao; 
	
	public JwtUserDetailsService(UserDao dao) {
		this.dao = dao;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = dao.getByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

}
