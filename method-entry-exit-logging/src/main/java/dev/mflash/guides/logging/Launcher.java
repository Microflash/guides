package dev.mflash.guides.logging;

import dev.mflash.guides.logging.service.GreetingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher implements CommandLineRunner {

  private final GreetingService greetingService;

  public Launcher(GreetingService greetingService) {
    this.greetingService = greetingService;
  }

  public static void main(String[] args) {
    SpringApplication.run(Launcher.class, args);
  }

  public @Override void run(String... args) throws Exception {
    greetingService.greet("Joe");
    greetingService.greet("Jane");
  }
}
