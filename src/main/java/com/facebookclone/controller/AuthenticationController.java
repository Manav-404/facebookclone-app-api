package com.facebookclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.facebookclone.model.User;
import com.facebookclone.service.serviceImp.JwtUserDetailsService;
import com.facebookclone.utils.JwtUtils;
import com.facebookclone.utils.RestResponse;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtils tokenUtils;

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

}
