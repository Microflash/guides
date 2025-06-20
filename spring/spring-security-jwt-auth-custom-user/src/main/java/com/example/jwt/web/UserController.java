package com.example.jwt.web;

import com.example.jwt.userdetails.CustomUser;
import com.example.jwt.userdetails.CustomUserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public record UserController(CustomUserDetailsService customUserDetailsService) {

	public static final String USER_ENDPOINT = "/user";

	@PostMapping(USER_ENDPOINT)
	public String register(@RequestBody CustomUser newUser) {
		customUserDetailsService.saveUser(newUser);
		return "User '%s' registered successfully".formatted(newUser.username());
	}
}
