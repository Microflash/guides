package dev.mflash.guides.mongo.configuration;

import dev.mflash.guides.mongo.helper.converter.DateToZonedDateTimeConverter;
import dev.mflash.guides.mongo.helper.converter.ZonedDateTimeToDateConverter;
import dev.mflash.guides.mongo.helper.event.CascadeMongoEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@EnableMongoRepositories(MongoConfiguration.REPOSITORY_PACKAGE)
public @Configuration class MongoConfiguration {

  static final String REPOSITORY_PACKAGE = "dev.mflash.guides.mongo.repository";
  private final List<Converter<?, ?>> converters = new ArrayList<>();

  public @Bean CascadeMongoEventListener cascadeMongoEventListener() {
    return new CascadeMongoEventListener();
  }

  public @Bean MongoCustomConversions customConversions() {
    converters.add(new DateToZonedDateTimeConverter());
    converters.add(new ZonedDateTimeToDateConverter());
    return new MongoCustomConversions(converters);
  }
}