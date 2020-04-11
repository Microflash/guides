package dev.mflash.guides.jwtauth.configuration.converter;

import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mapping.model.SimpleTypeHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JpaCustomConversions extends CustomConversions {

  private static final List<Object> STORE_CONVERTERS = Collections.emptyList();
  private static final StoreConversions STORE_CONVERSIONS = StoreConversions
      .of(new SimpleTypeHolder(Collections.emptySet(), true), STORE_CONVERTERS);

  public JpaCustomConversions() {
    this(Collections.emptyList());
  }

  public JpaCustomConversions(Collection<?> converters) {
    super(STORE_CONVERSIONS, converters);
  }
}
