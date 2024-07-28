package com.example;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public record Book(@Id UUID id, String title, Genre genre, String author) {
}
