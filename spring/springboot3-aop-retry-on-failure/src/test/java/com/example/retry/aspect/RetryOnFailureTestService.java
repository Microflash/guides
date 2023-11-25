package com.example.retry.aspect;

import com.example.retry.annotation.RetryOnFailure;
import org.springframework.stereotype.Service;

@Service
public class RetryOnFailureTestService {

	@RetryOnFailure
	public double attempt(double value) {
		if (value <= 0) {
			throw new ArithmeticException("Value <= 0");
		}

		return value;
	}
}
