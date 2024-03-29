package com.example.retry;

import com.example.retry.annotation.RetryOnFailure;
import org.springframework.stereotype.Service;

@Service
public class RandomlyFailingService {

	@RetryOnFailure
	public double random() {
		double random = Math.random();

		if (random <= 0.5) {
			throw new ArithmeticException("Value <= 0.5");
		}

		return random;
	}
}
