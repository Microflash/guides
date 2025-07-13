package com.example;

import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Singleton;

@Singleton
public class ScheduledEventService {

	private final ObjectMapper mapper = new ObjectMapper();

	record EventDetails(String message) {}

	public void process(ScheduledEvent event) {
		var eventDetails = mapper.convertValue(event.getDetail(), EventDetails.class);
		System.out.println(eventDetails.message());
	}
}
