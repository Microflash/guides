package dev.mflash.guides.pagila.controller;

import dev.mflash.guides.pagila.domain.Actor;
import dev.mflash.guides.pagila.service.ActorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequestMapping("/actor")
public @RestController class ActorController {

  private final ActorService actorService;

  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @GetMapping
  public Set<Actor> getAllActors() {
    return actorService.getAllActors();
  }

  @GetMapping("/{lastName}")
  public Set<Actor> getByLastName(@PathVariable String lastName) {
    return actorService.getByLastName(lastName);
  }
}
