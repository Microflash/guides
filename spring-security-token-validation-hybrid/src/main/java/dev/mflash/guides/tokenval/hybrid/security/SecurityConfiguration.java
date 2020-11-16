package dev.mflash.guides.tokenval.hybrid.security;

import static dev.mflash.guides.tokenval.hybrid.GenericController.*;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final OidcProperties oidcProps;
  private final OAuth2ResourceServerProperties resourceServerProps;

  public SecurityConfiguration(OidcProperties oidcProps, OAuth2ResourceServerProperties resourceServerProps) {
    this.oidcProps = oidcProps;
    this.resourceServerProps = resourceServerProps;
  }

  protected @Override void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .mvcMatchers(CONTEXT + PUBLIC_ENDPOINT).permitAll()
        .mvcMatchers(CONTEXT + PRIVATE_ENDPOINT_LOCAL).authenticated()
        .mvcMatchers(CONTEXT + PRIVATE_SCOPED_ENDPOINT_REMOTE).hasAuthority("SCOPE_read:messages")
        .and().oauth2ResourceServer().authenticationManagerResolver(customAuthenticationManager());
  }

  AuthenticationManagerResolver<HttpServletRequest> customAuthenticationManager() {
    var authenticationManagers = new LinkedHashMap<RequestMatcher, AuthenticationManager>();
    RequestMatcher requestMatcherLocal = request -> request.getRequestURI().contains(PRIVATE_SCOPED_ENDPOINT_REMOTE);
    authenticationManagers.put(requestMatcherLocal, opaque());

    var authenticationManagerResolver = new RequestMatchingAuthenticationManagerResolver(authenticationManagers);
    authenticationManagerResolver.setDefaultAuthenticationManager(jwt());

    return authenticationManagerResolver;
  }

  AuthenticationManager jwt() {
    var jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoder());
    jwtAuthenticationProvider.setJwtAuthenticationConverter(new JwtAuthenticationConverter());
    return jwtAuthenticationProvider::authenticate;
  }

  AuthenticationManager opaque() {
    return new OpaqueTokenAuthenticationProvider(tokenIntrospector())::authenticate;
  }

  @Bean JwtDecoder jwtDecoder() {
    String issuer = resourceServerProps.getJwt().getIssuerUri();
    String audience = oidcProps.getAudience();
    var jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuer);
    var audienceValidator = new CustomTokenValidator(audience);
    OAuth2TokenValidator<Jwt> validatorWithIssuer = JwtValidators.createDefaultWithIssuer(issuer);
    var validatorWithAudience = new DelegatingOAuth2TokenValidator<>(validatorWithIssuer, audienceValidator);
    jwtDecoder.setJwtValidator(validatorWithAudience);
    return jwtDecoder;
  }

  @Bean OpaqueTokenIntrospector tokenIntrospector() {
    return new CustomOpaqueTokenIntrospector(resourceServerProps);
  }
}
