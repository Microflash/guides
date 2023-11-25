package dev.mflash.guides.springdoc;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/note")
@Tag(name = "Note", description = "Endpoints for CRUD operations on notes")
public class NoteController {

  private final NoteRepository repository;

  public NoteController(NoteRepository repository) {
    this.repository = repository;
  }

  @PutMapping
  public List<Note> save(@RequestBody List<Note> notes) {
    List<Note> savedNotes = new ArrayList<>();
    repository.saveAll(notes).forEach(savedNotes::add);
    return savedNotes;
  }

  @GetMapping
  public List<Note> findAll() {
    List<Note> savedNotes = new ArrayList<>();
    repository.findAll().forEach(savedNotes::add);
    return savedNotes;
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public List<Note> upload(@RequestParam("data") MultipartFile csv) throws IOException {
    List<Note> savedNotes = new ArrayList<>();
    List<Note> notes = new BufferedReader(
        new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
        .map(Note::parseNote).collect(Collectors.toList());
    repository.saveAll(notes).forEach(savedNotes::add);
    return savedNotes;
  }

  @DeleteMapping("/{id}")
  public boolean delete(@PathVariable("id") long id) {
    repository.deleteById(id);
    return true;
  }
}
