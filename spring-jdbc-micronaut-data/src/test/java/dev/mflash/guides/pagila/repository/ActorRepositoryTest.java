package dev.mflash.guides.pagila.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest class ActorRepositoryTest {

  private @Autowired ActorRepository repository;

  @Test void findAll() {
    final var totalNumberOfRecords = 200;
    assertThat(repository.findAll().size()).isEqualTo(totalNumberOfRecords);
  }

  @Test void findByLastName() {
    final var results = repository.findByLastName("WAHLBERG");
    assertThat(results).isNotEmpty();
    assertThat(results).filteredOn(actor -> actor.getFirstName().equals("NICK")).isNotEmpty();
    assertThat(results).filteredOn(actor -> actor.getFirstName().equals("DARYL")).isNotEmpty();
  }
}