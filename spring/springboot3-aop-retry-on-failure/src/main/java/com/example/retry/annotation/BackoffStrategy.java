package com.example.retry.annotation;

public enum BackoffStrategy {
	NONE, RANDOM, LINEAR, LINEAR_RANDOM, GEOMETRIC, GEOMETRIC_RANDOM, EXPONENTIAL, EXPONENTIAL_RANDOM
}
