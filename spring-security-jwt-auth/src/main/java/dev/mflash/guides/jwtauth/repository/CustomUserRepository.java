package dev.mflash.guides.jwtauth.repository;

import dev.mflash.guides.jwtauth.domain.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository extends JpaRepository<CustomUser, Integer> {

  CustomUser findByEmail(String email);
}
