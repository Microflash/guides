package dev.mflash.guides.pagila.repository;

import dev.mflash.guides.pagila.domain.Actor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ActorRepository extends CrudRepository<Actor, Integer> {

  Set<Actor> findAll();

  @Query("SELECT * FROM Actor a WHERE a.last_name = :lastName")
  Set<Actor> findByLastName(String lastName);

}
