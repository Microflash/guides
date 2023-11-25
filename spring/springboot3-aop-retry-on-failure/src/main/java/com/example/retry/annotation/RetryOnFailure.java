package com.example.retry.annotation;

import io.github.resilience4j.core.IntervalFunction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryOnFailure {

	int attempts() default 3;

	BackoffStrategy strategy() default BackoffStrategy.EXPONENTIAL_RANDOM;

	long interval() default IntervalFunction.DEFAULT_INITIAL_INTERVAL;

	ChronoUnit unit() default ChronoUnit.MILLIS;

	double randomizationFactor() default IntervalFunction.DEFAULT_RANDOMIZATION_FACTOR;

	double multiplier() default IntervalFunction.DEFAULT_MULTIPLIER;

	Class<? extends Throwable>[] retryExceptions() default {Throwable.class};

	Class<? extends Throwable>[] ignoreExceptions() default {};
}
