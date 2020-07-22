package dev.mflash.guides.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestMessageBuilder {

  private int status;
  private String error;
  private String message;
  private String path;

  public RestMessageBuilder status(int status) {
    this.status = status;
    return this;
  }

  public RestMessageBuilder status(HttpStatus status) {
    this.status = status.value();

    if (status.isError()) {
      this.error = status.getReasonPhrase();
    }

    return this;
  }

  public RestMessageBuilder error(String error) {
    this.error = error;
    return this;
  }

  public RestMessageBuilder message(String message) {
    this.message = message;
    return this;
  }

  public RestMessageBuilder path(String path) {
    this.path = path;
    return this;
  }

  public RestMessage build() {
    return new RestMessage(status, error, message, path);
  }
}