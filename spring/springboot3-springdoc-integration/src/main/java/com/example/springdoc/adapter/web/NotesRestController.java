package com.example.springdoc.adapter.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(NotesRestController.CONTEXT)
@Tag(name = NotesRestController.CONTEXT, description = "Operations to manage notes")
@RequiredArgsConstructor
public class NotesRestController {

	static final String CONTEXT = "/v1/notes";

	private final NoteService noteService;

	@GetMapping
	@Operation(summary = "Returns a list of notes for a list of ids")
	public List<NoteWebView> query(@RequestParam Optional<List<UUID>> id) {
		return id.isPresent() && !id.get().isEmpty() ?
				noteService.queryByIds(id.get()) :
				noteService.queryAll();
	}

	@PutMapping
	@Operation(summary = "Creates a new note")
	public Optional<NoteWebView> createNewNote(@RequestBody NoteWebView note) {
		return noteService.create(note);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Creates a new note from the uploaded markdown file")
	public Optional<NoteWebView> uploadNewNote(@ModelAttribute NoteFileView note) {
		return noteService.create(note);
	}

	@PatchMapping
	@Operation(summary = "Patches an existing note")
	public Optional<NoteWebView> editNote(@RequestBody NoteWebView note) {
		return noteService.edit(note);
	}

	@DeleteMapping("/{ids}")
	@Operation(summary = "Deletes notes for a list of ids")
	public List<NoteWebView> deleteNotesById(@PathVariable List<UUID> ids) {
		return noteService.deleteByIds(ids);
	}
}
