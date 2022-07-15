package dev.mflash.guides.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.FileSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

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

  public @Bean OpenApiCustomiser openApiCustomiser() {
    return openApi -> openApi.getPaths()
        .values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
        .forEach(operation -> {
          if ("multipart-upload".equals(operation.getOperationId())) {
            operation.getRequestBody()
                .setContent(
                    new Content().addMediaType(
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType()
                            .schema(new ObjectSchema().addProperties("data", new FileSchema()))
                    )
                );
          }
        });
  }
}
