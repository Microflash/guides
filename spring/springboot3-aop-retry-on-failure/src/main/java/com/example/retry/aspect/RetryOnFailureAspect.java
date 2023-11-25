package com.example.retry.aspect;

import com.example.retry.annotation.BackoffStrategy;
import com.example.retry.annotation.RetryOnFailure;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

@Aspect
@Component
public class RetryOnFailureAspect {

	@Around("@annotation(com.example.retry.annotation.RetryOnFailure)")
	public Object retry(ProceedingJoinPoint point) throws Throwable {
		var methodSignature = (MethodSignature) point.getSignature();
		Method method = methodSignature.getMethod();
		var target = Modifier.isStatic(method.getModifiers()) ? method.getDeclaringClass() : point.getTarget();
		var methodName = method.getName();

		Logger logger = LoggerFactory.getLogger(target.getClass());
		var annotation = method.getAnnotation(RetryOnFailure.class);
		int attempts = annotation.attempts();
		Class<? extends Throwable>[] retryExceptions = annotation.retryExceptions();
		Class<? extends Throwable>[] ignoreExceptions = annotation.ignoreExceptions();
		RetryConfig.Builder<Object> retryConfigBuilder = RetryConfig.custom()
				.maxAttempts(attempts)
				.retryExceptions(retryExceptions)
				.ignoreExceptions(ignoreExceptions);

		BackoffStrategy backoffStrategy = annotation.strategy();

		if (backoffStrategy.equals(BackoffStrategy.NONE)) {
			long interval = annotation.interval();
			ChronoUnit unit = annotation.unit();
			retryConfigBuilder.waitDuration(Duration.of(interval, unit));
		} else {
			var intervalFunction = RetryOnFailureIntervalFunctions.of(annotation);
			retryConfigBuilder.intervalFunction(intervalFunction);
		}

		RetryConfig retryConfiguration = retryConfigBuilder.build();
		var retryRegistry = RetryRegistry.of(retryConfiguration);
		var retry = retryRegistry.retry(methodName, retryConfiguration);
		var publisher = retry.getEventPublisher();
		publisher.onRetry(event -> logger.warn(event.toString()));

		Supplier<Object> responseSupplier = Retry.decorateSupplier(retry, getProceed(point));

		return responseSupplier.get();
	}

	private static Supplier<Object> getProceed(ProceedingJoinPoint point) {
		return () -> {
			try {
				return point.proceed();
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		};
	}
}
