package com.example.developingtheclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class Chapter08DevelopingTheClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chapter08DevelopingTheClientApplication.class, args);
	}


//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		return http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
//				.oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/taco-admin-client"))
//				.oauth2Client(Customizer.withDefaults())
//				.build();
//	}
}
