package dev.mflash.guides.jwtauth.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.servlet.function.RouterFunctions.route;

import dev.mflash.guides.jwtauth.domain.CustomUser;
import dev.mflash.guides.jwtauth.repository.CustomUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public @Controller class CustomUserController {

  private final CustomUserRepository repository;
  private final PasswordEncoder passwordEncoder;

  public CustomUserController(CustomUserRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  private ServerResponse register(ServerRequest request) throws ServletException, IOException {
    final CustomUser customUser = request.body(CustomUser.class);
    customUser.setPassword(passwordEncoder.encode(customUser.getPassword()));
    repository.save(customUser);
    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(Map.of("message", "Successfully saved " + customUser.getName()));
  }

  public @Bean RouterFunction<ServerResponse> customUserRouter() {
    return route()
        .POST("/users/register", this::register)
        .build();
  }
}
