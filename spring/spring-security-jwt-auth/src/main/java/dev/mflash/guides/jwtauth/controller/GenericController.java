package dev.mflash.guides.jwtauth.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.servlet.function.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;

public @Controller class GenericController {

  public static final String PUBLIC_ENDPOINT_URL = "/jwt/public";
  public static final String PRIVATE_ENDPOINT_URL = "/jwt/private";

  private ServerResponse publicEndpoint(ServerRequest request) {
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(messageMap("public"));
  }

  private ServerResponse privateEndpoint(ServerRequest request) {
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(messageMap("private"));
  }

  private Map<String, String> messageMap(String type) {
    return Map.of("message", String.format("Hello, world! This is a %s endpoint", type));
  }

  public @Bean RouterFunction<ServerResponse> genericRoutes() {
    return route()
        .GET(PUBLIC_ENDPOINT_URL, this::publicEndpoint)
        .GET(PRIVATE_ENDPOINT_URL, this::privateEndpoint)
        .build();
  }
}
