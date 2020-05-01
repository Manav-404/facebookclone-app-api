package com.facebookclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.facebookclone.dao.FriendsProcessDao;


@SpringBootApplication(scanBasePackages = {"com.facebookclone"})
public class FacebookCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacebookCloneApplication.class, args);
	}
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				// TODO Auto-generated method stub
//				registry.addMapping("/**").allowedHeaders("*").allowedOrigins("*").allowedMethods("*")
//				.allowCredentials(true);
//			}
//		};
//	}
	
}