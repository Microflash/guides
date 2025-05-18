package com.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(value = { "id" })
public record Book(@Id UUID id, String title, String author, List<String> genre) {}
