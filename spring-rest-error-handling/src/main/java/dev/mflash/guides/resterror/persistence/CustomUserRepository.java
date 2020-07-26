package dev.mflash.guides.resterror.persistence;

import dev.mflash.guides.resterror.domain.CustomUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {

  Optional<CustomUser> findByEmail(String email);
}
