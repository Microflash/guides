package dev.mflash.guides.mongo.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor
public class CascadeSaveCallback implements FieldCallback {

  private final Object source;
  private final MongoOperations mongoOperations;

  public @Override void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
    ReflectionUtils.makeAccessible(field);

    if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(Cascade.class)) {
      final Object fieldValue = field.get(source);

      if (Objects.nonNull(fieldValue)) {
        final var callback = new IdentifierCallback();
        final CascadeType cascadeType = field.getAnnotation(Cascade.class).value();

        if (cascadeType.equals(CascadeType.SAVE) || cascadeType.equals(CascadeType.ALL)) {
          if (fieldValue instanceof Collection<?>) {
            ((Collection<?>) fieldValue).forEach(mongoOperations::save);
          } else {
            ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
            mongoOperations.save(fieldValue);
          }
        }
      }
    }
  }
}
