package com.facebookclone.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EntityScan(basePackages = {"com.facebookclone.model"})
@EnableConfigurationProperties
@SpringBootApplication
public class FacebookCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacebookCloneApplication.class, args);
	}

}