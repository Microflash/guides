package dev.mflash.guides.tokenval.local.security;

import static dev.mflash.guides.tokenval.local.GenericController.*;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
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

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final String audience;
  private final String issuer;

  public SecurityConfiguration(OidcProperties oidcProps, OAuth2ResourceServerProperties resourceServerProps) {
    this.audience = oidcProps.getAudience();
    this.issuer = resourceServerProps.getJwt().getIssuerUri();
  }

  protected @Override void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .mvcMatchers(CONTEXT + PUBLIC_ENDPOINT).permitAll()
        .mvcMatchers(CONTEXT + PRIVATE_ENDPOINT).authenticated()
        .mvcMatchers(CONTEXT + PRIVATE_SCOPED_ENDPOINT).hasAuthority("SCOPE_read:messages")
        .and().oauth2ResourceServer().jwt();
  }

  @Bean JwtDecoder jwtDecoder() {
    var jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuer);
    var audienceValidator = new CustomTokenValidator(audience);
    OAuth2TokenValidator<Jwt> validatorWithIssuer = JwtValidators.createDefaultWithIssuer(issuer);
    var validatorWithAudience = new DelegatingOAuth2TokenValidator<>(validatorWithIssuer, audienceValidator);
    jwtDecoder.setJwtValidator(validatorWithAudience);
    return jwtDecoder;
  }
}
