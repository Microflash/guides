package dev.mflash.guides.mongo.helper.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.ZonedDateTime;
import java.util.Date;

public class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {

  public @Override Date convert(ZonedDateTime source) {
    return Date.from(source.toInstant());
  }
}
