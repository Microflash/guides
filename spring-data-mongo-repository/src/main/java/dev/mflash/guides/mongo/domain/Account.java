package dev.mflash.guides.mongo.domain;

import dev.mflash.guides.mongo.helper.event.Cascade;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class Account {

  private @Id String key;
  private String address;
  private @DBRef @Cascade User user;
  private @DBRef @Cascade Set<Session> sessions;
  private ZonedDateTime created;

  public Account() {
  }

  public Account(Builder builder) {
    this.address = builder.address;
    this.user = builder.user;
    this.sessions = builder.sessions;
    this.created = builder.created;
  }

  public String getKey() {
    return key;
  }

  public User getUser() {
    return user;
  }

  public Set<Session> getSessions() {
    return sessions;
  }

  public static class Builder {

    private String address;
    private User user;
    private Set<Session> sessions;
    private ZonedDateTime created;

    public Builder() {
      this.created = ZonedDateTime.now();
      this.sessions = new HashSet<>();
    }

    public Builder address(String address) {
      this.address = address;
      return this;
    }

    public Builder user(User user) {
      this.user = user;
      return this;
    }

    public Builder session(Session session) {
      this.sessions.add(session);
      return this;
    }

    public Account build() {
      return new Account(this);
    }
  }
}
