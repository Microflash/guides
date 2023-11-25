package com.example.notes.adapter.web;

import com.example.notes.adapter.database.Note;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.util.UUID;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record NoteWebView(
		UUID id,
		String title,
		String body,
		String createdBy,
		String updatedBy,
		long wordCount,
		boolean readOnly
) {

	public static NoteWebView convert(Note note) {
		return NoteWebView.builder()
				.id(note.id())
				.title(note.title())
				.body(note.body())
				.createdBy(note.createdBy())
				.updatedBy(note.updatedBy())
				.wordCount(note.wordCount())
				.readOnly(note.readOnly())
				.build();
	}
}
