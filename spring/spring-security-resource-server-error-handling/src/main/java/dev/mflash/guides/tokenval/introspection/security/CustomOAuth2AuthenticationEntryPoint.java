package dev.mflash.guides.tokenval.introspection.security;

import static dev.mflash.guides.tokenval.introspection.security.WWWAuthenticateHeaderBuilder.computeWWWAuthenticateHeaderValue;

import dev.mflash.guides.tokenval.introspection.exception.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CustomOAuth2AuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2AuthenticationEntryPoint.class);

  private String realmName;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException {
    logger.error(e.getLocalizedMessage(), e);

    HttpStatus status = HttpStatus.UNAUTHORIZED;
    String errorMessage = "Insufficient authentication details";
    Map<String, String> parameters = new LinkedHashMap<>();

    if (Objects.nonNull(realmName)) {
      parameters.put("realm", realmName);
    }

    if (e instanceof OAuth2AuthenticationException) {
      OAuth2Error error = ((OAuth2AuthenticationException) e).getError();
      parameters.put("error", error.getErrorCode());

      if (StringUtils.hasText(error.getDescription())) {
        errorMessage = error.getDescription();
        parameters.put("error_description", errorMessage);
      }

      if (StringUtils.hasText(error.getUri())) {
        parameters.put("error_uri", error.getUri());
      }

      if (error instanceof BearerTokenError) {
        BearerTokenError bearerTokenError = (BearerTokenError) error;

        if (StringUtils.hasText(bearerTokenError.getScope())) {
          parameters.put("scope", bearerTokenError.getScope());
        }

        status = ((BearerTokenError) error).getHttpStatus();
      }
    }

    String message = RestResponse.builder()
        .status(status)
        .error("Unauthenticated")
        .message(errorMessage)
        .path(request.getRequestURI())
        .json();

    String wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);
    response.addHeader("WWW-Authenticate", wwwAuthenticate);
    response.setStatus(status.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(message);
  }
}
