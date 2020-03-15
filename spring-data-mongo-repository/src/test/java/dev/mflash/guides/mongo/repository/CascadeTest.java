package dev.mflash.guides.mongo.repository;

import static org.junit.jupiter.api.Assertions.*;

import dev.mflash.guides.mongo.domain.Account;
import dev.mflash.guides.mongo.domain.User;
import dev.mflash.guides.mongo.domain.User.Builder;
import dev.mflash.guides.mongo.domain.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

@ExtendWith(SpringExtension.class)
public @SpringBootTest class CascadeTest {

  private @Autowired AccountRepository accountRepository;
  private @Autowired SessionRepository sessionRepository;
  private @Autowired UserRepository userRepository;
  private User jasmine;
  private Session paris;
  private Account saved;

  public @BeforeEach void setUp() {
    accountRepository.deleteAll();
    sessionRepository.deleteAll();
    userRepository.deleteAll();

    jasmine = new Builder().name("Jasmine Beck").locale(Locale.FRANCE)
        .dateOfBirth(LocalDate.of(1995, Month.DECEMBER, 12)).build();
    paris = new Session.Builder().city("Paris").locale(Locale.FRANCE).build();
    Account account = new Account.Builder().address("jasmine@nos.com").user(jasmine).session(paris)
        .build();

    saved = accountRepository.save(account);
  }

  public @Test void saveCascade() {
    userRepository.findById(saved.getUser().getKey())
        .ifPresent(user -> assertEquals(jasmine, user));
    saved.getSessions().stream().findFirst().ifPresent(session -> assertEquals(paris, session));
  }

  public @Test void deleteCascade() {
    Account account = accountRepository.findDistinctFirstByUser(jasmine);
    accountRepository.deleteById(account.getKey());

    account.getSessions()
        .forEach(session -> assertTrue(sessionRepository.findById(session.getKey()).isEmpty()));
    assertTrue(userRepository.findById(account.getUser().getKey()).isEmpty());
  }
}