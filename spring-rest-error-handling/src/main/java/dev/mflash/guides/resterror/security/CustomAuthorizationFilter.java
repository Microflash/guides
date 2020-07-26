package dev.mflash.guides.resterror.security;

import static dev.mflash.guides.resterror.security.CustomAuthenticationFilter.AUTH_HEADER_KEY;
import static dev.mflash.guides.resterror.security.TokenManager.*;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CustomAuthorizationFilter extends BasicAuthenticationFilter {

  private final AccessDeniedHandler accessDeniedHandler;

  public CustomAuthorizationFilter(AuthenticationManager authenticationManager, AccessDeniedHandler accessDeniedHandler) {
    super(authenticationManager);
    this.accessDeniedHandler = accessDeniedHandler;
  }

  protected @Override void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    String header = request.getHeader(AUTH_HEADER_KEY);

    if (Objects.isNull(header) || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    if (header.startsWith(TOKEN_PREFIX)) {
      try {
        var authentication = getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
      } catch (Exception e) {
        accessDeniedHandler.handle(request, response, new AccessDeniedException(e.getLocalizedMessage(), e));
      }
    }
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String header) {

    if (header.startsWith(TOKEN_PREFIX)) {
      String username = parseToken(header);
      return new UsernamePasswordAuthenticationToken(username, null, List.of());
    } else {
      throw new AccessDeniedException("Failed to parse authentication token");
    }
  }
}
