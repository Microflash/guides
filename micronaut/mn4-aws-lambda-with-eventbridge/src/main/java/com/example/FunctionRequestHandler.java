package com.example;

import io.micronaut.function.aws.MicronautRequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import jakarta.inject.Inject;

public class FunctionRequestHandler extends MicronautRequestHandler<ScheduledEvent, Void> {

	private @Inject ScheduledEventService eventService;

	@Override
	public Void execute(ScheduledEvent event) {
		this.eventService.process(event);
		return null;
	}
}
