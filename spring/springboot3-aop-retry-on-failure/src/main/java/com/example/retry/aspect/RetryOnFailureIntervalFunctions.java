package com.example.retry.aspect;

import com.example.retry.annotation.BackoffStrategy;
import com.example.retry.annotation.RetryOnFailure;
import io.github.resilience4j.core.IntervalFunction;

import java.util.function.Function;

public class RetryOnFailureIntervalFunctions {

	public static IntervalFunction of(RetryOnFailure retry) {

		BackoffStrategy strategy = retry.strategy();
		long interval = retry.interval();
		double randomizationFactor = retry.randomizationFactor();
		double multiplier = retry.multiplier();

		return switch (strategy) {
			case RANDOM -> ofRandom(interval, randomizationFactor);
			case LINEAR -> ofLinear(interval);
			case LINEAR_RANDOM -> ofLinearRandom(interval, randomizationFactor);
			case GEOMETRIC -> ofGeometric(interval, multiplier);
			case GEOMETRIC_RANDOM -> ofGeometricRandom(interval, multiplier, randomizationFactor);
			case EXPONENTIAL -> ofExponential(interval);
			default -> ofExponentialRandom(interval, multiplier, randomizationFactor);
		};
	}

	private static IntervalFunction ofRandom(long interval, double randomizationFactor) {
		return IntervalFunction.ofRandomized(interval, randomizationFactor);
	}

	private static IntervalFunction ofLinear(long interval) {
		Function<Long, Long> linearBackoffFn = previous -> previous + interval;
		return IntervalFunction.of(interval, linearBackoffFn);
	}

	private static IntervalFunction ofLinearRandom(long interval, double randomizationFactor) {
		return attempt -> (long) randomize(ofLinear(interval).apply(attempt), randomizationFactor);
	}

	private static IntervalFunction ofGeometric(long interval, double multiplier) {
		Function<Long, Long> geometricBackoffFn = previous -> Math.round(previous * multiplier);
		return IntervalFunction.of(interval, geometricBackoffFn);
	}

	private static IntervalFunction ofGeometricRandom(long interval, double multiplier, double randomizationFactor) {
		return attempt -> (long) randomize(ofGeometric(interval, multiplier).apply(attempt), randomizationFactor);
	}

	private static IntervalFunction ofExponential(long interval) {
		return IntervalFunction.ofExponentialBackoff(interval);
	}

	private static IntervalFunction ofExponentialRandom(long interval, double multiplier, double randomizationFactor) {
		return IntervalFunction.ofExponentialRandomBackoff(interval, multiplier, randomizationFactor);
	}

	static double randomize(double current, double randomizationFactor) {
		final double delta = randomizationFactor * current;
		final double min = current - delta;
		final double max = current + delta;

		return (min + (Math.random() * (max - min + 1)));
	}
}
