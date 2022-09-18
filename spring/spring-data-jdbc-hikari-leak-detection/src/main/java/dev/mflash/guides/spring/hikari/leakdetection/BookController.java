package dev.mflash.guides.spring.hikari.leakdetection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

	private final BookRepository repository;

	public BookController(BookRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/{genre}")
	public List<Book> getAllBooksByGenre(@PathVariable Genre genre) {
		return repository.findAllByGenre(genre).collect(Collectors.toList());
	}
}
