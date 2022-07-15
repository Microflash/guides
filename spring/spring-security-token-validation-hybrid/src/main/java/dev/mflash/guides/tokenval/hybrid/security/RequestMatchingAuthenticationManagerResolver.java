package dev.mflash.guides.tokenval.hybrid.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestMatchingAuthenticationManagerResolver implements AuthenticationManagerResolver<HttpServletRequest> {

  private final LinkedHashMap<RequestMatcher, AuthenticationManager> authenticationManagers;
  private AuthenticationManager defaultAuthenticationManager = authentication -> {
    throw new AuthenticationServiceException("Cannot authenticate " + authentication);
  };

  public RequestMatchingAuthenticationManagerResolver(
      LinkedHashMap<RequestMatcher, AuthenticationManager> authenticationManagers) {
    this.authenticationManagers = authenticationManagers;
  }

  public @Override AuthenticationManager resolve(HttpServletRequest context) {
    for (Map.Entry<RequestMatcher, AuthenticationManager> entry : this.authenticationManagers.entrySet()) {
      if (entry.getKey().matches(context)) {
        return entry.getValue();
      }
    }

    return this.defaultAuthenticationManager;
  }

  public void setDefaultAuthenticationManager(AuthenticationManager defaultAuthenticationManager) {
    this.defaultAuthenticationManager = defaultAuthenticationManager;
  }
}
