package dev.mflash.guides.resterror.exception;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class RestResponse {

  private final LocalDateTime timestamp = LocalDateTime.now();
  private int status;
  private String error;
  private String message;
  private String path;

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public static RestResponseBuilder builder() {
    return new RestResponseBuilder();
  }

  public String toJson() {
    return new StringJoiner(", ", "{", "}")
        .add("\"timestamp\": \"" + timestamp + "\"")
        .add("\"status\": " + status)
        .add("\"error\": \"" + error + "\"")
        .add("\"message\": \"" + message + "\"")
        .add("\"path\": \"" + path + "\"")
        .toString();
  }
}
