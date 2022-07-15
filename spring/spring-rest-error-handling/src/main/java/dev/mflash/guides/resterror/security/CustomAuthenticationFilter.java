package dev.mflash.guides.resterror.security;

import static dev.mflash.guides.resterror.security.TokenManager.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mflash.guides.resterror.domain.CustomUser;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  public static final String AUTH_HEADER_KEY = "Authorization";

  private final AuthenticationManager authenticationManager;

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public @Override Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    try {
      var user = new ObjectMapper().readValue(request.getInputStream(), CustomUser.class);
      return authenticationManager.authenticate(CustomUserConverter.toAuthenticationToken(user));
    } catch (IOException e) {
      throw new AuthenticationCredentialsNotFoundException("Failed to resolve authentication credentials", e);
    }
  }

  protected @Override void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    response.addHeader(AUTH_HEADER_KEY,
        TOKEN_PREFIX + generateToken(((User) authResult.getPrincipal()).getUsername()));
  }
}
