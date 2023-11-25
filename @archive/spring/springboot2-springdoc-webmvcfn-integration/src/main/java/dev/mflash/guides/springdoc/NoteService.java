package dev.mflash.guides.springdoc;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Note", description = "Endpoints for CRUD operations on notes")
public @Service class NoteService {

  private final NoteRepository repository;

  public NoteService(NoteRepository repository) {
    this.repository = repository;
  }

  public List<Note> save(List<Note> notes) {
    List<Note> savedNotes = new ArrayList<>();
    repository.saveAll(notes).forEach(savedNotes::add);
    return savedNotes;
  }

  public List<Note> findAll() {
    List<Note> savedNotes = new ArrayList<>();
    repository.findAll().forEach(savedNotes::add);
    return savedNotes;
  }

  public List<Note> upload(Part csv) throws IOException {
    List<Note> savedNotes = new ArrayList<>();
    List<Note> notes = new BufferedReader(
        new InputStreamReader(csv.getInputStream(), StandardCharsets.UTF_8)).lines()
        .map(Note::parseNote).collect(Collectors.toList());
    repository.saveAll(notes).forEach(savedNotes::add);
    return savedNotes;
  }

  public boolean delete(@Parameter(in = ParameterIn.PATH) long id) {
    repository.deleteById(id);
    return true;
  }
}
