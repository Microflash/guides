package dev.mflash.guides.mongo.repository;

import static dev.mflash.guides.mongo.configuration.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public @SpringBootTest class AccountRepositoryTest {

  private @Autowired AccountRepository repository;

  public @BeforeEach void setUp() {
    repository.deleteAll();
    repository.saveAll(account.values());
  }

  public @Test void findAll() {
    final var totalNumberOfRecords = 3;
    assertEquals(totalNumberOfRecords, repository.findAll().size());
  }

  public @Test void setKeyOnSave() {
    repository.findAll().forEach(account -> assertNotNull(account.getKey()));
  }

  public @Test void findDistinctFirstByUserName() {
    final var tinaLawrence = Name.TINA_LAWRENCE;
    assertEquals(users.get(tinaLawrence),
        repository.findDistinctFirstByUser(users.get(tinaLawrence)).getUser());
  }

  public @Test void findBySessionLocale() {
    final var numberOfResults = 2;
    assertEquals(numberOfResults, repository.findBySessions(sessions.get(City.LOS_ANGELES)).size());
  }

  public @Test void findByAddress() {
    final var address = Address.MOHD_ALI;
    assertEquals(Name.MOHD_ALI.name, repository.findByAddress(address.email).getUser().getName());
  }
}