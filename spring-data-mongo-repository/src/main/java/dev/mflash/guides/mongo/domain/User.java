package dev.mflash.guides.mongo.domain;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class User {

  private @Id String key;
  private String name;
  private Locale locale;
  private LocalDate dateOfBirth;

  public User() {
  }

  public User(Builder builder) {
    this.name = builder.name;
    this.locale = builder.locale;
    this.dateOfBirth = builder.dateOfBirth;
  }

  public String getKey() {
    return key;
  }

  public String getName() {
    return name;
  }

  public @Override boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return key.equals(user.key) &&
        name.equals(user.name) &&
        locale.equals(user.locale) &&
        dateOfBirth.equals(user.dateOfBirth);
  }

  public @Override int hashCode() {
    return Objects.hash(key, name, locale, dateOfBirth);
  }

  public static class Builder {

    private String name;
    private Locale locale;
    private LocalDate dateOfBirth;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder locale(Locale locale) {
      this.locale = locale;
      return this;
    }

    public Builder dateOfBirth(LocalDate dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }
}
