package com.example;

import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionRequestHandlerTest {

	@AutoClose
	private final ApplicationContext context = ApplicationContext.run();
	private final FunctionRequestHandler handler = context.getBean(FunctionRequestHandler.class);

	@Test
	@DisplayName("Should print message in event detail")
	void shouldPrintMessageInEventDetail() throws IOException {
		try (var outputStream = new ByteArrayOutputStream();
				 var redirectedStream = new PrintStream(outputStream)) {
			System.setOut(redirectedStream);

			String message = "Hello World!";
			var event = new ScheduledEvent();
			event.setDetail(Map.of("message", message));
			handler.execute(event);

			String loggedMessage = outputStream.toString().trim();
			assertEquals(message, loggedMessage);
		}
	}
}
