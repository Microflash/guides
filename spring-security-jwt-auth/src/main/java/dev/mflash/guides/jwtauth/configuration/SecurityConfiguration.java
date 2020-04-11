package dev.mflash.guides.jwtauth.configuration;

import dev.mflash.guides.jwtauth.configuration.security.JWTAuthenticationFilter;
import dev.mflash.guides.jwtauth.configuration.security.JWTAuthorizationFilter;
import dev.mflash.guides.jwtauth.configuration.security.TokenManager;
import dev.mflash.guides.jwtauth.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final CustomUserDetailsService userDetailsService;

  public SecurityConfiguration(CustomUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  public @Bean PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  protected @Override void configure(HttpSecurity http) throws Exception {
    http.cors().and()
        .csrf().disable()
        .authorizeRequests()
          .antMatchers(HttpMethod.POST, TokenManager.SIGN_UP_URL).permitAll()
        .anyRequest().authenticated().and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager()))
        .addFilter(new JWTAuthorizationFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  protected @Override void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  public @Bean CorsConfigurationSource corsConfigurationSource() {
    final var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
