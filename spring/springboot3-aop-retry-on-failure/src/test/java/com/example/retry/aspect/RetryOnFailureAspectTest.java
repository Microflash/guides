package com.example.retry.aspect;

import ch.qos.logback.classic.Logger;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.DefaultAopProxyFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SoftAssertionsExtension.class)
class RetryOnFailureAspectTest {

	private RetryOnFailureTestService service;
	private AspectAppender appender;

	@BeforeEach
	void setUp() {
		var retryAspect = new RetryOnFailureAspect();
		var aspectJProxyFactory = new AspectJProxyFactory(new RetryOnFailureTestService());
		aspectJProxyFactory.addAspect(retryAspect);
		var aopProxy = new DefaultAopProxyFactory().createAopProxy(aspectJProxyFactory);

		service = (RetryOnFailureTestService) aopProxy.getProxy();
		appender = new AspectAppender();
		appender.start();

		var logger = (Logger) LoggerFactory.getLogger(RetryOnFailureTestService.class);
		logger.addAppender(appender);
	}

	@AfterEach
	void tearDown() {
		appender.stop();
	}

	@Test
	@DisplayName("Advice should fire with retries on failure")
	void adviceShouldFireWithRetriesOnFailure(SoftAssertions softly) {
		var thrown = catchThrowable(() -> service.attempt(0));

		softly.assertThat(appender.events).hasSize(2);
		softly.assertThat(thrown).isInstanceOf(RuntimeException.class);
		softly.assertThat(appender.events.stream().anyMatch(
				event -> event.getMessage().contains("Retry 'attempt', waiting") && event.getMessage()
						.contains("Last attempt failed with exception"))).isTrue();
	}

	@Test
	@DisplayName("Advice should not fire on success")
	void adviceShouldNotFireOnSuccess() {
		service.attempt(1);
		service.attempt(2);

		assertThat(appender.events).isEmpty();
	}
}
