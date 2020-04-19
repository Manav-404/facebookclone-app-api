package com.facebookclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebookclone.dto.UserDto;
import com.facebookclone.model.User;
import com.facebookclone.service.UserService;
import com.facebookclone.service.serviceImp.JwtUserDetailsService;
import com.facebookclone.service.serviceImp.UserServiceImp;
import com.facebookclone.utils.JwtUtils;
import com.facebookclone.utils.RestResponse;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtils tokenUtils;
	
	@Autowired
	private UserServiceImp userService;

	@PostMapping("/authenticate")
	public RestResponse authenticate(@RequestBody User user) throws Exception {
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
					);
		}catch(BadCredentialsException e) {
			throw new Exception("Incorrect Uusername or password");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
		final String jwt = tokenUtils.generateToken(userDetails); 
		
		return RestResponse.builder().data(jwt).status(true).build();
		
	}
	
	@PostMapping("/signup")
	public RestResponse signup(@RequestBody User user) {
		UserDto dto = userService.createUser(user);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
		final String jwt = tokenUtils.generateToken(userDetails); 
		dto.setSignInToken(jwt);
		return RestResponse.builder().data(dto).status(true).build();
		
	}

}
