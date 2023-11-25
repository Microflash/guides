package dev.mflash.guides.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

public @SpringBootApplication class Launcher {

  public static void main(String[] args) {
    SpringApplication.run(Launcher.class, args);
  }

  public @Bean OpenAPI noteAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Note API")
                .description("A CRUD API to demonstrate Springdoc integration")
                .version("0.0.1-SNAPSHOT")
                .license(
                    new License().name("MIT").url("https://opensource.org/licenses/MIT")
                )
        );
  }
}
