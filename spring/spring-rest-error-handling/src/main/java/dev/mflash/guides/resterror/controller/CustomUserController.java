package dev.mflash.guides.resterror.controller;

import dev.mflash.guides.resterror.domain.CustomUser;
import dev.mflash.guides.resterror.persistence.CustomUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(CustomUserController.CTX)
public @RestController class CustomUserController {

  public static final String CTX = "/user";
  public static final String REGISTRATION_URL = CTX + "/register";

  private final CustomUserRepository repository;
  private final PasswordEncoder passwordEncoder;

  public CustomUserController(CustomUserRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping(REGISTRATION_URL)
  public Map<String, String> register(CustomUser newUser) {
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    repository.save(newUser);
    return Map.of("message", String.format("Registration successful for %s", newUser.getName()));
  }
}
