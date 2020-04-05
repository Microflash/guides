package dev.mflash.guides.pagila.service;

import dev.mflash.guides.pagila.repository.ActorRepository;
import dev.mflash.guides.pagila.domain.Actor;
import org.springframework.stereotype.Service;

import java.util.Set;

public @Service class ActorService {

  private final ActorRepository repository;

  public ActorService(ActorRepository repository) {
    this.repository = repository;
  }

  public Set<Actor> getAllActors() {
    return repository.findAll();
  }

  public Set<Actor> getByLastName(String lastName) {
    return repository.findByLastName(lastName.toUpperCase());
  }
}
