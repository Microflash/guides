package dev.mflash.guides.mongo.domain;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@Data @Builder
public class User {

  private final @Id @Default String key = UUID.randomUUID().toString();
  private String name;
  private String email;
  private Locale locale;
  private LocalDate dateOfBirth;
}
