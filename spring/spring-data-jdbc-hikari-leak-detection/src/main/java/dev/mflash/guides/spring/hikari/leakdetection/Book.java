package dev.mflash.guides.spring.hikari.leakdetection;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public record Book(@Id UUID id, String title, Genre genre, String author) {
}
