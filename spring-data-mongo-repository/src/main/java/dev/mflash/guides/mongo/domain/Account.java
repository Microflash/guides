package dev.mflash.guides.mongo.domain;

import dev.mflash.guides.mongo.event.Cascade;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Singular;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Data @Builder
public class Account {

  private final @Id @Default String key = UUID.randomUUID().toString();
  private @DBRef @Cascade User user;
  private @DBRef @Cascade @Singular Set<Session> sessions;
  private ZonedDateTime created;
}
