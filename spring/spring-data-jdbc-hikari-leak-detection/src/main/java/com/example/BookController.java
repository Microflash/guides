package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public record BookController(BookRepository repository) {

	@GetMapping
	public List<Book> booksByGenre(@RequestParam String genre) {
		return repository.findAllByGenre(genre).toList();
	}
}
