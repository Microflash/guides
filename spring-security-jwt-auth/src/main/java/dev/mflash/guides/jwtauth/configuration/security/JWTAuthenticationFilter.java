package dev.mflash.guides.jwtauth.configuration.security;

import static dev.mflash.guides.jwtauth.configuration.security.TokenManager.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mflash.guides.jwtauth.domain.CustomUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public @Override Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    try {
      CustomUser customUser = new ObjectMapper().readValue(request.getInputStream(), CustomUser.class);

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(customUser.getEmail(), customUser.getPassword(), Collections.emptyList()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected @Override void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    response.addHeader(HEADER_STRING, TOKEN_PREFIX + generateToken(((User) authResult.getPrincipal()).getUsername()));
  }
}
