package com.example.notes.adapter.database;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.StringTokenizer;
import java.util.UUID;

@Table("notes")
@Builder
public record Note(
		@Id
		UUID id,
		String title,
		String body,
		String createdBy,
		LocalDateTime createdAt,
		String updatedBy,
		LocalDateTime updatedAt,
		long version,
		long wordCount,
		boolean readOnly) {

	@Override
	public long wordCount() {
		return countWords(body);
	}

	public static long countWords(String text) {
		return new StringTokenizer(text).countTokens();
	}
}
