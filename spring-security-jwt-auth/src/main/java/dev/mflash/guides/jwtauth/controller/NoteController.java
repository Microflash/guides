package dev.mflash.guides.jwtauth.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.servlet.function.RouterFunctions.route;

import dev.mflash.guides.jwtauth.domain.Note;
import dev.mflash.guides.jwtauth.repository.NoteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public @Controller class NoteController {

  private final NoteRepository repository;

  public NoteController(NoteRepository repository) {
    this.repository = repository;
  }

  private ServerResponse addNote(ServerRequest request) throws ServletException, IOException {
    final Note note = request.body(Note.class);
    note.setLastUpdate(OffsetDateTime.now());
    final int id = repository.save(note).getId();
    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(Map.of("message", "Saved '" + note.getTitle() + "' with id: " + id));
  }

  private ServerResponse getNotes(ServerRequest request) {
    final List<Note> notes = repository.findAll();
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(notes);
  }

  private ServerResponse editNote(ServerRequest request) throws ServletException, IOException {
    final int id = Integer.parseInt(request.pathVariable("id"));
    final Note editedNote = request.body(Note.class);
    repository.findById(id).ifPresent(note -> {
      final Note newNote = new Note();
      newNote.setId(id);
      newNote.setTitle(!editedNote.getTitle().equals(note.getTitle()) ? editedNote.getTitle() : note.getTitle());
      newNote
          .setContent(!editedNote.getContent().equals(note.getContent()) ? editedNote.getContent() : note.getContent());
      newNote.setLastUpdate(OffsetDateTime.now());

      repository.save(newNote);
    });

    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(Map.of("message", "Saved edits for '" + editedNote.getTitle() + "'"));
  }

  private ServerResponse deleteNote(ServerRequest request) {
    final int id = Integer.parseInt(request.pathVariable("id"));
    repository.deleteById(id);
    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(Map.of("message", "Successfully deleted note with id: " + id));
  }

  public @Bean RouterFunction<ServerResponse> notesRouter() {
    return route()
        .nest(RequestPredicates.path("/notes"),
            builder -> builder.POST("/", this::addNote)
              .GET("/", this::getNotes)
              .PUT("/{id}", this::editNote)
              .DELETE("/{id}", this::deleteNote).build())
        .build();
  }
}
