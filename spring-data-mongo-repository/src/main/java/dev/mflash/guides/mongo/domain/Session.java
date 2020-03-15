package dev.mflash.guides.mongo.domain;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.UUID;

public class Session {

  private @Id String key;
  private String city;
  private Locale locale;
  private String fingerprint;
  private LocalDate lastAccessedOn;
  private LocalTime lastAccessedAt;

  public Session() {
  }

  public Session(Builder builder) {
    this.city = builder.city;
    this.locale = builder.locale;
    this.fingerprint = builder.fingerprint;
    this.lastAccessedOn = builder.lastAccessedOn;
    this.lastAccessedAt = builder.lastAccessedAt;
  }

  public String getKey() {
    return key;
  }

  public static class Builder {

    private String city;
    private Locale locale;
    private String fingerprint;
    private LocalDate lastAccessedOn;
    private LocalTime lastAccessedAt;

    public Builder() {
      this.fingerprint = UUID.randomUUID().toString();
      this.lastAccessedOn = LocalDate.now();
      this.lastAccessedAt = LocalTime.now();
    }

    public Builder city(String city) {
      this.city = city;
      return this;
    }

    public Builder locale(Locale locale) {
      this.locale = locale;
      return this;
    }

    public Session build() {
      return new Session(this);
    }
  }
}
