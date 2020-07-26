package dev.mflash.guides.resterror.security;

import static dev.mflash.guides.resterror.controller.CustomUserController.REGISTRATION_URL;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String LOGIN_URL = "/user/login";
  private final CustomUserDetailsService userDetailsService;

  public SecurityConfiguration(CustomUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  protected @Override void configure(HttpSecurity http) throws Exception {
    http.cors().and()
        .csrf().disable()
        .authorizeRequests().antMatchers(POST, REGISTRATION_URL).permitAll()
        .anyRequest().authenticated().and()
        .addFilter(new CustomAuthenticationFilter(authenticationManager()))
        .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).and()
        .addFilter(new CustomAuthorizationFilter(authenticationManager(), new CustomAccessDeniedHandler()))
        .sessionManagement().sessionCreationPolicy(STATELESS);
  }

  public @Bean PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
