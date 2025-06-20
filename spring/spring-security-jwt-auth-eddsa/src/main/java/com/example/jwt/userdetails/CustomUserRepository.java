package com.example.jwt.userdetails;

import org.springframework.data.repository.CrudRepository;

public interface CustomUserRepository extends CrudRepository<CustomUser, String> {

	CustomUser findByUsername(String username);
}
