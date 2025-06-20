package com.example.jwt.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	public static final String PUBLIC_ENDPOINT = "/public";
	public static final String PRIVATE_ENDPOINT = "/private";

	@GetMapping(PUBLIC_ENDPOINT)
	public String greetPublic() {
		return "Hello, World!";
	}

	@GetMapping(PRIVATE_ENDPOINT)
	public String greetPrivate(Authentication authentication) {
		return "Hello, " + authentication.getName() + "!";
	}
}
