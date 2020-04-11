package dev.mflash.guides.jwtauth.configuration;

import dev.mflash.guides.jwtauth.configuration.converter.JpaCustomConversions;
import dev.mflash.guides.jwtauth.configuration.converter.OffsetDateTimeToTimestampConverter;
import dev.mflash.guides.jwtauth.configuration.converter.TimestampToOffsetDateTimeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;

@EnableJpaRepositories("dev.mflash.guides.jwtauth.repository")
public class DatabaseConfiguration {

  @Primary
  public @Bean JpaCustomConversions customConversions() {
    final var converters = new ArrayList<Converter<?, ?>>();
    converters.add(TimestampToOffsetDateTimeConverter.INSTANCE);
    converters.add(OffsetDateTimeToTimestampConverter.INSTANCE);
    return new JpaCustomConversions(converters);
  }
}
