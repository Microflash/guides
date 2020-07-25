package dev.mflash.guides.jwtauth.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;

import dev.mflash.guides.jwtauth.domain.Book;
import dev.mflash.guides.jwtauth.persistence.BookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public @Controller class BookController {

  private final BookRepository repository;

  public BookController(BookRepository repository) {
    this.repository = repository;
  }

  private ServerResponse addBook(ServerRequest request) throws ServletException, IOException {
    final Book newBook = request.body(Book.class);
    repository.save(newBook);
    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(Map.of("message", String.format("Save successful for %s", newBook.getTitle())));
  }

  private ServerResponse getAllBooks(ServerRequest request) {
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(repository.findAll());
  }

  private ServerResponse editBook(ServerRequest request) throws ServletException, IOException {
    long id = Long.parseLong(request.pathVariable("id"));
    final Book editedBook = request.body(Book.class);
    final Optional<Book> saved = repository.findById(id);

    if (saved.isPresent()) {
      final Book savedBook = saved.get();
      final Book patchedBook = new Book();
      patchedBook.setId(savedBook.getId());
      patchedBook.setTitle(
          !editedBook.getTitle().equals(savedBook.getTitle()) ? editedBook.getTitle() : savedBook.getTitle());
      patchedBook.setAuthor(
          !editedBook.getAuthor().equals(savedBook.getAuthor()) ? editedBook.getAuthor() : savedBook.getAuthor());
      patchedBook.setGenre(
          !editedBook.getGenre().equals(savedBook.getGenre()) ? editedBook.getGenre() : savedBook.getGenre());
      repository.save(patchedBook);
    } else {
      throw new InvalidDataAccessApiUsageException("Couldn't patch unrelated or non-existent records");
    }

    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(Map.of("message", String.format("Patch successful for %s", editedBook.getTitle())));
  }

  private ServerResponse deleteBook(ServerRequest request) {
    long id = Long.parseLong(request.pathVariable("id"));

    if (repository.deleteById(id) < 1) {
      throw new InvalidDataAccessApiUsageException("Couldn't delete a non-existent record");
    }

    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(Map.of("message", String.format("Deletion successful for book with id: %s", id)));
  }

  public @Bean RouterFunction<ServerResponse> bookRoutes() {
    return route()
        .nest(path("/book"),
            builder -> builder
                .GET("/", this::getAllBooks)
                .PUT("/", this::addBook)
                .PATCH("/{id}", this::editBook)
                .DELETE("/{id}", this::deleteBook))
        .build();
  }
}
