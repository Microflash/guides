package dev.mflash.guides.jwtauth.service;

import dev.mflash.guides.jwtauth.domain.CustomUser;
import dev.mflash.guides.jwtauth.repository.CustomUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

public @Service class CustomUserDetailsService implements UserDetailsService {

  private final CustomUserRepository repository;

  public CustomUserDetailsService(CustomUserRepository repository) {
    this.repository = repository;
  }

  public @Override UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    CustomUser customUser = Objects.requireNonNull(repository.findByEmail(email), () -> {
      throw new UsernameNotFoundException(email);
    });

    return new User(customUser.getEmail(), customUser.getPassword(), Collections.emptyList());
  }
}
