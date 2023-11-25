package com.example.notes.adapter.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NoteFileView(String title, MultipartFile body, String createdBy, boolean readOnly) {

	public NoteFileView {
		if (!Objects.requireNonNull(body.getContentType()).equals("text/markdown")) {
			throw new RuntimeException("Illegal file format");
		}
	}

	public String content() {
		try {
			return new String(body.getBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read file", e);
		}
	}
}
