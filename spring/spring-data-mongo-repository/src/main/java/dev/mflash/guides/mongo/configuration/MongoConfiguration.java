package dev.mflash.guides.mongo.configuration;

import dev.mflash.guides.mongo.event.AccountCascadeMongoEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(MongoConfiguration.REPOSITORY_PACKAGE)
public @Configuration class MongoConfiguration {

  static final String REPOSITORY_PACKAGE = "dev.mflash.guides.mongo.repository";

  public @Bean AccountCascadeMongoEventListener cascadeMongoEventListener() {
    return new AccountCascadeMongoEventListener();
  }

  public @Bean MongoCustomConversions customConversions() {
    return new MongoCustomConversions(ZonedDateTimeConverters.getConvertersToRegister());
  }
}
