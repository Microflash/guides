package dev.mflash.guides.pagila.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import java.util.ArrayList;

@EnableJdbcRepositories(basePackages = "dev.mflash.guides.pagila.repository")
public @Configuration class DatabaseConfiguration {

  @Primary
  public @Bean JdbcCustomConversions customConversions() {
    final var converters = new ArrayList<Converter<?, ?>>();
    converters.add(new TimestampToOffsetDateTimeConverter());
    converters.add(new OffsetDateTimeToTimestampConverter());
    return new JdbcCustomConversions(converters);
  }
}
