package dev.mflash.guides.jwtauth.configuration.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@ReadingConverter
public enum TimestampToOffsetDateTimeConverter implements Converter<Timestamp, OffsetDateTime> {
  INSTANCE;

  public @Override OffsetDateTime convert(Timestamp source) {
    return source.toInstant().atOffset(ZoneOffset.UTC);
  }
}
