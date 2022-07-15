package dev.mflash.guides.customer;

import io.micronaut.runtime.Micronaut;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public @SpringBootApplication class Launcher {

  public static void main(String... args) {
    Micronaut.run(Launcher.class);
  }
}
