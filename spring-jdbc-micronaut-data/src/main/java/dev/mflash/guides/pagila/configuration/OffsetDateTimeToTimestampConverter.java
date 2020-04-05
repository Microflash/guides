package dev.mflash.guides.pagila.configuration;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;

public class OffsetDateTimeToTimestampConverter implements Converter<OffsetDateTime, Timestamp> {

  public @Override Timestamp convert(OffsetDateTime source) {
    return Timestamp.from(Instant.from(source));
  }
}
