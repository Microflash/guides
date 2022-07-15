package dev.mflash.guides.mongo.repository;

import static org.assertj.core.api.Assertions.*;

import dev.mflash.guides.mongo.domain.Account;
import dev.mflash.guides.mongo.domain.Session;
import dev.mflash.guides.mongo.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;

@ExtendWith(SpringExtension.class)
@SpringBootTest class AccountRepositoryTest {

  private static final List<User> SAMPLE_USERS = List.of(
      User.builder().name("Tina Lawrence").email("tina@example.com").locale(Locale.CANADA).dateOfBirth(
          LocalDate.of(1989, Month.JANUARY, 11)).build(),
      User.builder().name("Adrian Chase").email("adrian@example.com").locale(Locale.UK).dateOfBirth(
          LocalDate.of(1994, Month.APRIL, 23)).build(),
      User.builder().name("Mohd Ali").email("mohdali@example.com").locale(Locale.JAPAN).dateOfBirth(
          LocalDate.of(1999, Month.OCTOBER, 9)).build()
  );

  private static final List<Session> SAMPLE_SESSIONS = List.of(
      Session.builder().city("Toronto").locale(Locale.CANADA).build(),
      Session.builder().city("Los Angeles").locale(Locale.US).build(),
      Session.builder().city("London").locale(Locale.UK).build(),
      Session.builder().city("Paris").locale(Locale.FRANCE).build(),
      Session.builder().city("Tokyo").locale(Locale.JAPAN).build()
  );

  private static final List<Account> SAMPLE_ACCOUNTS = List.of(
      Account.builder().user(SAMPLE_USERS.get(0)).session(SAMPLE_SESSIONS.get(0)).session(SAMPLE_SESSIONS.get(1))
          .created(ZonedDateTime.now()).build(),
      Account.builder().user(SAMPLE_USERS.get(1)).session(SAMPLE_SESSIONS.get(1)).session(SAMPLE_SESSIONS.get(2))
          .created(ZonedDateTime.now()).build(),
      Account.builder().user(SAMPLE_USERS.get(2)).session(SAMPLE_SESSIONS.get(4)).session(SAMPLE_SESSIONS.get(3))
          .created(ZonedDateTime.now()).build()
  );

  private @Autowired AccountRepository repository;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
    repository.saveAll(SAMPLE_ACCOUNTS);
  }

  @Test
  @DisplayName("Should find some accounts")
  void shouldFindSomeAccounts() {
    assertThat(repository.count()).isEqualTo(SAMPLE_ACCOUNTS.size());
  }

  @Test
  @DisplayName("Should assign a key on save")
  void shouldAssignAKeyOnSave() {
    assertThat(repository.findAll()).extracting("key").isNotNull();
  }

  @Test
  @DisplayName("Should get a distinct user by first name")
  void shouldGetADistinctUserByFirstName() {
    assertThat(repository.findDistinctFirstByUser(SAMPLE_USERS.get(0)).getUser())
        .isEqualToIgnoringGivenFields(SAMPLE_USERS.get(0), "key");
  }

  @Test
  @DisplayName("Should find some users with a given session")
  void shouldFindSomeUsersWithAGivenSession() {
    assertThat(repository.findBySessions(SAMPLE_SESSIONS.get(1))).isNotEmpty();
  }
}
