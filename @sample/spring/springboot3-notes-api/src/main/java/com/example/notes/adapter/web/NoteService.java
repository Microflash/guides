package com.example.notes.adapter.web;

import com.example.notes.adapter.database.Note;
import com.example.notes.adapter.database.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {

	private final NotesRepository repository;

	public List<NoteWebView> queryAll() {
		return repository.findAll().stream().map(NoteWebView::convert).toList();
	}

	public List<NoteWebView> queryByIds(List<UUID> ids) {
		return repository.findAllByIdIn(ids).stream().map(NoteWebView::convert).toList();
	}

	public Optional<NoteWebView> create(NoteWebView note) {
		return repository.save(
				note.title(), note.body(), note.createdBy(),
				Note.countWords(note.body()), note.readOnly()
		).map(NoteWebView::convert);
	}

	public Optional<NoteWebView> create(NoteFileView note) {
		String body = note.content();
		return repository.save(
				note.title(), body, note.createdBy(),
				Note.countWords(body), note.readOnly()
		).map(NoteWebView::convert);
	}

	public Optional<NoteWebView> edit(NoteWebView note) {
		if (Objects.isNull(note.id())) {
			throw new IllegalArgumentException("Can't edit a note without id");
		}

		return repository.update(
				note.id(), note.title(), note.body(), note.updatedBy(),
				Objects.nonNull(note.body()) && !note.body().isBlank() ? Note.countWords(note.body()) : 0,
				note.readOnly()
		).map(NoteWebView::convert);
	}

	public List<NoteWebView> deleteByIds(List<UUID> ids) {
		return repository.deleteAllByIdIn(ids).map(NoteWebView::convert).toList();
	}
}
