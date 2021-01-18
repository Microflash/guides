package dev.mflash.guides.tokenval.introspection.security;

import static dev.mflash.guides.tokenval.introspection.GenericController.*;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  protected @Override void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .mvcMatchers(CONTEXT + PUBLIC_ENDPOINT).permitAll()
        .mvcMatchers(CONTEXT + PRIVATE_ENDPOINT).authenticated()
        .mvcMatchers(CONTEXT + PRIVATE_SCOPED_ENDPOINT).hasAuthority("SCOPE_read:messages")
        .and().oauth2ResourceServer()
        .authenticationEntryPoint(new CustomOAuth2AuthenticationEntryPoint())
        .accessDeniedHandler(new CustomOAuth2AccessDeniedHandler())
        .opaqueToken();
  }

  @Bean OpaqueTokenIntrospector tokenIntrospector(OAuth2ResourceServerProperties resourceServerProps) {
    return new CustomOpaqueTokenIntrospector(resourceServerProps);
  }
}
