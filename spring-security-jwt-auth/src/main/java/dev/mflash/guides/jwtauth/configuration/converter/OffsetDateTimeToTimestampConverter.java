package dev.mflash.guides.jwtauth.configuration.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;

@WritingConverter
public enum OffsetDateTimeToTimestampConverter implements Converter<OffsetDateTime, Timestamp> {
  INSTANCE;

  public @Override Timestamp convert(OffsetDateTime source) {
    return Timestamp.from(Instant.from(source));
  }
}
