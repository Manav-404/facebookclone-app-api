package com.facebookclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.facebookclone.dto.ProfileDto;
import com.facebookclone.model.Profile;
import com.facebookclone.service.ProfileService;
import com.facebookclone.utils.RestResponse;

@RestController
@RequestMapping("/api")
public class ProfileController {
	
	@Autowired
	private ProfileService service ;
	
	@GetMapping("/profile/{id}")
	public RestResponse getProfile(@PathVariable("id") long id) throws Exception {
		ProfileDto dto  = service.getProfileByUser(id);
		return RestResponse.builder().data(dto)
				.status(true).build();
	}
	
	@PutMapping("/editProfile/{id}")
	public RestResponse editProfile(@RequestBody Profile profile , @PathVariable("id") long id) {
		ProfileDto dto = service.editProfile(profile, id);
		return RestResponse.builder().data(dto).status(true)
				.build();
		
	}
	
	@PostMapping("/profile")
	public RestResponse createProfile(@RequestBody Profile profile , @RequestHeader(value = "Authorization") String token) throws Exception {
		
		ProfileDto create = service.createProfile(profile ,token);
			return RestResponse.builder().data(create).status(false).build();		

	}
	
	@PostMapping("/uploadPhoto/{id}")
	public RestResponse uploadPhoto(@PathVariable("id") long id, @RequestParam("file")MultipartFile file ,@RequestHeader(value = "Authorization") String token) throws Exception {
		String path = service.uploadProfilePhoto(file, id , token);
		return RestResponse.builder().data(path).status(true).build();
	}
	
	@GetMapping("/search/{key}")
	public RestResponse searchFriends(@PathVariable("key")String name) {
		ProfileDto dto = service.getBySearch(name);
		return RestResponse.builder().data(dto).status(true).build();
	}

	
	
}
