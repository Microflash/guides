package dev.mflash.guides.mongo.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest class AccountCascadeTest {

  private static final User SAMPLE_USER = User.builder().name("Jasmine Beck").email("jasmine@example.com").locale(
      Locale.FRANCE).dateOfBirth(LocalDate.of(1995, Month.DECEMBER, 12)).build();
  private static final Session SAMPLE_SESSION = Session.builder().city("Paris").locale(Locale.FRANCE).build();
  private static final Account SAMPLE_ACCOUNT = Account.builder().user(SAMPLE_USER).session(SAMPLE_SESSION).created(
      ZonedDateTime.now()).build();

  private @Autowired AccountRepository accountRepository;
  private @Autowired SessionRepository sessionRepository;
  private @Autowired UserRepository userRepository;

  private Account savedAccount;

  @BeforeEach
  void setUp() {
    accountRepository.deleteAll();
    sessionRepository.deleteAll();
    userRepository.deleteAll();
    savedAccount = accountRepository.save(SAMPLE_ACCOUNT);
  }

  @Test
  @DisplayName("Should cascade on save")
  void shouldCascadeOnSave() {
    final User savedUser = savedAccount.getUser();
    final Optional<Session> savedSession = savedAccount.getSessions().stream().findAny();

    final String userId = savedUser.getKey();
    assertThat(userRepository.findById(userId))
        .hasValueSatisfying(user -> assertThat(user).isEqualToIgnoringGivenFields(SAMPLE_USER, "key"));

    if (savedSession.isPresent()) {
      final String sessionId = savedSession.get().getKey();
      assertThat(sessionRepository.findById(sessionId)).isNotEmpty()
          .hasValueSatisfying(session -> assertThat(session).isEqualToIgnoringGivenFields(SAMPLE_SESSION, "key"));
    }

    savedUser.setLocale(Locale.CANADA);
    savedAccount.setUser(savedUser);
    accountRepository.save(savedAccount);
    assertThat(userRepository.findById(userId))
        .hasValueSatisfying(user -> assertThat(user.getLocale()).isEqualTo(Locale.CANADA));

    if (savedSession.isPresent()) {
      final Session modifiedSession = savedSession.get();
      modifiedSession.setCity("Nice");
      savedAccount.setSessions(Set.of(modifiedSession, Session.builder().city("Lyon").locale(Locale.FRANCE).build()));
      Account modifiedAccount = accountRepository.save(savedAccount);
      assertThat(sessionRepository.findById(modifiedSession.getKey())).isNotEmpty()
          .hasValueSatisfying(session -> assertThat(session.getCity()).isEqualTo("Nice"));
      assertThat(modifiedAccount.getSessions().stream().filter(s -> s.getCity().equals("Lyon")).findAny())
          .hasValueSatisfying(session -> assertThat(sessionRepository.findById(session.getKey())).isNotEmpty()
              .hasValueSatisfying(matchedSession -> assertThat(matchedSession.getCity()).isEqualTo("Lyon")));
    }
  }

  @Test
  @DisplayName("Should not cascade on fetch")
  void shouldNotCascadeOnFetch() {
    final String userId = savedAccount.getUser().getKey();
    final Set<Session> sessions = savedAccount.getSessions();
    accountRepository.findById(savedAccount.getKey());

    assertThat(userRepository.findById(userId)).isNotEmpty();
    assertThat(sessions).allSatisfy(session ->
        assertThat(sessionRepository.findById(session.getKey())).isNotEmpty());
  }

  @Test
  @DisplayName("Should cascade on delete")
  void shouldCascadeOnDelete() {
    final Optional<Account> fetchedAccount = accountRepository.findById(savedAccount.getKey());
    accountRepository.deleteById(savedAccount.getKey());

    assertThat(fetchedAccount)
        .hasValueSatisfying(account -> {
          assertThat(userRepository.findById(account.getUser().getKey())).isEmpty();
          assertThat(account.getSessions())
              .allSatisfy(session -> assertThat(sessionRepository.findById(session.getKey())).isEmpty());
        });
  }
}
