package dev.mflash.guides.tokenval.introspection.security;

import static dev.mflash.guides.tokenval.introspection.security.WWWAuthenticateHeaderBuilder.computeWWWAuthenticateHeaderValue;

import dev.mflash.guides.tokenval.introspection.exception.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CustomOAuth2AccessDeniedHandler implements AccessDeniedHandler {

  public static final Logger logger = LoggerFactory.getLogger(CustomOAuth2AccessDeniedHandler.class);

  private String realmName;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
      throws IOException {
    logger.error(e.getLocalizedMessage(), e);

    Map<String, String> parameters = new LinkedHashMap<>();
    String errorMessage = e.getLocalizedMessage();

    if (Objects.nonNull(realmName)) {
      parameters.put("realm", realmName);
    }

    if (request.getUserPrincipal() instanceof AbstractOAuth2TokenAuthenticationToken) {
      errorMessage = "The request requires higher privileges than provided by the access token.";

      parameters.put("error", "insufficient_scope");
      parameters.put("error_description", errorMessage);
      parameters.put("error_uri", "https://tools.ietf.org/html/rfc6750#section-3.1");
    }

    String message = RestResponse.builder()
        .status(HttpStatus.FORBIDDEN)
        .message(errorMessage)
        .path(request.getRequestURI())
        .json();

    String wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);
    response.addHeader("WWW-Authenticate", wwwAuthenticate);
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(message);
  }

  public void setRealmName(String realmName) {
    this.realmName = realmName;
  }
}
