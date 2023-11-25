package com.example.retry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/random")
public class RandomlyFailingController {

	private final RandomlyFailingService service;

	public RandomlyFailingController(RandomlyFailingService service) {
		this.service = service;
	}

	@GetMapping
	public double getRandom() {
		return service.random();
	}
}
