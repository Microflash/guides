package dev.mflash.guides.jwtauth.security;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {

  Optional<CustomUser> findByEmail(String email);
}
