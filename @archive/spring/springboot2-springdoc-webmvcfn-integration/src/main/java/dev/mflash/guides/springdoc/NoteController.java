package dev.mflash.guides.springdoc;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.servlet.function.RouterFunctions.route;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

public @Controller class NoteController {

  private final NoteService service;

  public NoteController(NoteService service) {
    this.service = service;
  }

  public ServerResponse save(ServerRequest request) throws ServletException, IOException {
    final List<Note> newNotes = request.body(new ParameterizedTypeReference<>() {});
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(service.save(newNotes));
  }

  public ServerResponse findAll(ServerRequest request) {
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(service.findAll());
  }

  public ServerResponse upload(ServerRequest request) throws IOException, ServletException {
    Part csv = request.servletRequest().getPart("data");
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(service.upload(csv));
  }

  public ServerResponse delete(ServerRequest request) {
    long id = Long.parseLong(request.pathVariable("id"));
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(service.delete(id));
  }

  @RouterOperations({
    @RouterOperation(path = "/note", method = PUT, beanClass = NoteService.class, beanMethod = "save"),
    @RouterOperation(path = "/note", method = GET, beanClass = NoteService.class, beanMethod = "findAll"),
    @RouterOperation(path = "/note", method = POST,
                     operation = @Operation(
                         operationId = "multipart-upload",
                         requestBody = @RequestBody(required = true, description = "Upload a csv of notes"),
                         responses = @ApiResponse()
                     ),
                     beanClass = NoteService.class, beanMethod = "upload"),
    @RouterOperation(path = "/note/{id}", method = DELETE, beanClass = NoteService.class, beanMethod = "delete")
  })
  public @Bean RouterFunction<ServerResponse> routes() {
    return route()
        .nest(RequestPredicates.path("/note"),
            builder -> builder.PUT("/", this::save)
                .GET("/", this::findAll)
                .POST("/", RequestPredicates.accept(MULTIPART_FORM_DATA), this::upload)
                .DELETE("/{id}", this::delete).build())
        .build();
  }
}
