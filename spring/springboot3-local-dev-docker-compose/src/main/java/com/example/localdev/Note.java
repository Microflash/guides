package com.example.localdev;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("notes")
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record Note(
		@Id
		UUID id,
		String title,
		String body,
		boolean readOnly) {
}
