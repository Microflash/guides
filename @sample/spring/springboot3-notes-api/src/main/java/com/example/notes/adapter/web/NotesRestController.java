package com.example.notes.adapter.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(NotesRestController.CONTEXT)
@RequiredArgsConstructor
public class NotesRestController {

	static final String CONTEXT = "/v1/notes";

	private final NoteService noteService;

	@GetMapping
	public List<NoteWebView> query(@RequestParam Optional<List<UUID>> id) {
		return id.isPresent() && !id.get().isEmpty() ?
				noteService.queryByIds(id.get()) :
				noteService.queryAll();
	}

	@PutMapping
	public Optional<NoteWebView> createNewNote(@RequestBody NoteWebView note) {
		return noteService.create(note);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Optional<NoteWebView> uploadNewNote(@ModelAttribute NoteFileView note) {
		return noteService.create(note);
	}

	@PatchMapping
	public Optional<NoteWebView> editNote(@RequestBody NoteWebView note) {
		return noteService.edit(note);
	}

	@DeleteMapping("/{ids}")
	public List<NoteWebView> deleteNotesById(@PathVariable List<UUID> ids) {
		return noteService.deleteByIds(ids);
	}
}
