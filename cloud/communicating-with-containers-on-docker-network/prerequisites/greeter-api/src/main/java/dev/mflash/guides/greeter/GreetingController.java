package dev.mflash.guides.greeter;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller("/hello")
public class GreetingController {

  @Get
  public Greeting greet(@QueryValue(value = "name", defaultValue = "World") String name) {
    return new Greeting(String.format("Hello, %s!", name));
  }
}
