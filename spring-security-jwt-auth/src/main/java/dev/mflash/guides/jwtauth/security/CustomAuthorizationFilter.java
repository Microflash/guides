package dev.mflash.guides.jwtauth.security;

import static dev.mflash.guides.jwtauth.security.CustomAuthenticationFilter.AUTH_HEADER_KEY;
import static dev.mflash.guides.jwtauth.security.TokenManager.*;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CustomAuthorizationFilter extends BasicAuthenticationFilter {

  public CustomAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  protected @Override void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    String header = request.getHeader(AUTH_HEADER_KEY);

    if (Objects.isNull(header) || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String header = request.getHeader(AUTH_HEADER_KEY);

    if (Objects.nonNull(header) && header.startsWith(TOKEN_PREFIX)) {
      try {
        String username = parseToken(header);
        return new UsernamePasswordAuthenticationToken(username, null, List.of());
      } catch (ExpiredJwtException e) {
        throw new AccessDeniedException("Expired token");
      } catch (UnsupportedJwtException | MalformedJwtException e) {
        throw new AccessDeniedException("Unsupported token");
      } catch (Exception e) {
        throw new AccessDeniedException("User authorization not resolved");
      }
    } else {
      throw new AccessDeniedException("Authorization token not found");
    }
  }
}
