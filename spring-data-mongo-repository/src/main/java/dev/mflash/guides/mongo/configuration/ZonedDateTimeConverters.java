package dev.mflash.guides.mongo.configuration;

import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class ZonedDateTimeConverters {

  public static List<Converter<?, ?>> getConvertersToRegister() {
    return List.of(ZonedDateTimeToDateConverter.INSTANCE, DateToZonedDateTimeConverter.INSTANCE);
  }

  private enum ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {
    INSTANCE;

    public @Override Date convert(ZonedDateTime source) {
      return Date.from(source.toInstant());
    }
  }

  private enum DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {
    INSTANCE;

    public @Override ZonedDateTime convert(Date source) {
      return source.toInstant().atZone(ZoneOffset.UTC);
    }
  }
}
