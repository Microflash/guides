package dev.mflash.guides.mongo.helper.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {

  public @Override ZonedDateTime convert(Date source) {
    return source.toInstant().atZone(ZoneOffset.UTC);
  }
}
