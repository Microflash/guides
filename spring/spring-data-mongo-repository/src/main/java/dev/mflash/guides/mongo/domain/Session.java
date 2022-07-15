package dev.mflash.guides.mongo.domain;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Data @Builder
public class Session {

  private final @Id @Default String key = UUID.randomUUID().toString();
  private String city;
  private Locale locale;
  private LocalDateTime accessed;
}
