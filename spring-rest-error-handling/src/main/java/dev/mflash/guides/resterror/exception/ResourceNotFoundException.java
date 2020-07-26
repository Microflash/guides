package dev.mflash.guides.resterror.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.server.ResponseStatusException;

public class ResourceNotFoundException extends ResponseStatusException {

  public ResourceNotFoundException() {
    super(NOT_FOUND);
  }

  public ResourceNotFoundException(String reason) {
    super(NOT_FOUND, reason);
  }

  public ResourceNotFoundException(String reason, Throwable cause) {
    super(NOT_FOUND, reason, cause);
  }
}
