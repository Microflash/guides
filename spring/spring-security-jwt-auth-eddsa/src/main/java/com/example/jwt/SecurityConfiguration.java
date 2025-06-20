package com.example.jwt;

import com.example.jwt.web.GreetingController;
import com.example.jwt.web.TokenController;
import com.example.jwt.web.UserController;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.PrivateJwk;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Configuration
public class SecurityConfiguration {

	private final JwtProperties jwtProperties;

	SecurityConfiguration(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.GET, GreetingController.PUBLIC_ENDPOINT).permitAll()
						.requestMatchers(UserController.USER_ENDPOINT).permitAll()
						.anyRequest().authenticated()
				)
				.csrf((csrf) -> csrf.ignoringRequestMatchers(TokenController.TOKEN_ENDPOINT))
				.httpBasic(Customizer.withDefaults())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exceptions -> exceptions
						.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
						.accessDeniedHandler(new BearerTokenAccessDeniedHandler())
				);
		return http.build();
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return new CustomJwtDecoder(jwtProperties.publicKey());
	}

	@Bean
	JwtEncoder jwtEncoder() {
		PrivateJwk<PrivateKey, PublicKey, ?> jwk = Jwks.builder()
				.keyPair(new KeyPair(jwtProperties.publicKey(), jwtProperties.privateKey()))
				.algorithm(jwtProperties.privateKey().getAlgorithm())
				.build();
		var jwkSet = Jwks.set().add(jwk).build();
		return new CustomJwtEncoder(jwkSet);
	}
}
