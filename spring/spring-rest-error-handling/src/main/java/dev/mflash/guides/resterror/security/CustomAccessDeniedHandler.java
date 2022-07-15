package dev.mflash.guides.resterror.security;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import dev.mflash.guides.resterror.exception.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

  public @Override void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
      throws IOException {
    logger.error(e.getLocalizedMessage(), e);

    String message = RestResponse.builder()
        .status(FORBIDDEN)
        .message("Invalid Authorization token")
        .path(request.getRequestURI())
        .json();

    response.setStatus(FORBIDDEN.value());
    response.setContentType(APPLICATION_JSON_VALUE);
    response.getWriter().write(message);
  }
}
