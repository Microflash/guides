package dev.mflash.guides.exception.service;

import dev.mflash.guides.exception.handler.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

public @Service class ErrorService {

  private static final String MESSAGE_TEMPLATE = "Sending a runtime exception for %s service";

  public Map<String, String> get() {
    final String response = Math.random() > 0.5 ? "Hello" : "Bye";

    if (response.equals("Bye")) {
      throw new CustomException(String.format(MESSAGE_TEMPLATE, "get"));
    }

    return Map.of("message", response);
  }

  public Map<String, String> post() {
    final String response = Math.random() > 0.5 ? "Hello" : "Bye";

    if (response.equals("Bye")) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(MESSAGE_TEMPLATE, "post"));
    }

    return Map.of("message", response);
  }
}
