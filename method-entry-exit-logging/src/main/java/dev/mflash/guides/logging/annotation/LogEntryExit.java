package dev.mflash.guides.logging.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

@Retention(RUNTIME)
@Target(METHOD)
public @interface LogEntryExit {

  LogLevel value() default LogLevel.INFO;

  ChronoUnit unit() default ChronoUnit.SECONDS;

  boolean showArgs() default false;

  boolean showResult() default false;

  boolean showExecutionTime() default true;
}
