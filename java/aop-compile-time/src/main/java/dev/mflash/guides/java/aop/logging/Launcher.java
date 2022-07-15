package dev.mflash.guides.java.aop.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Launcher {

  private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

  private static final GreetingHandler handler = new GreetingHandler();

  public static void main(String... args) {
    logger.info("{}", handler.greet("Veronica"));
  }
}
