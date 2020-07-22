package dev.mflash.guides.exception.handler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  ResponseEntity<?> handleResponseStatusException(ResponseStatusException exception, HttpServletRequest request,
      WebRequest webRequest) {

    return handleExceptionInternal(
        exception,
        new RestMessageBuilder()
            .status(exception.getStatus())
            .message(exception.getReason())
            .path(request.getRequestURI())
            .build(),
        exception.getResponseHeaders(),
        exception.getStatus(),
        webRequest
    );
  }

  @ExceptionHandler(CustomException.class)
  ResponseEntity<?> handleCustomException(CustomException exception, HttpServletRequest request,
      WebRequest webRequest) {

    return handleExceptionInternal(
        exception,
        new RestMessageBuilder()
            .status(INTERNAL_SERVER_ERROR)
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .build(),
        HttpHeaders.EMPTY,
        INTERNAL_SERVER_ERROR,
        webRequest
    );
  }
}
