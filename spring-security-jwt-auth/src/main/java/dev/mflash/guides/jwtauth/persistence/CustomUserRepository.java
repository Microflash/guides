package dev.mflash.guides.jwtauth.persistence;

import dev.mflash.guides.jwtauth.domain.CustomUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {

  Optional<CustomUser> findByEmail(String email);
}
