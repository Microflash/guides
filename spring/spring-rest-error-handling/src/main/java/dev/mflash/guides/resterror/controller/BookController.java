package dev.mflash.guides.resterror.controller;

import dev.mflash.guides.resterror.domain.Book;
import dev.mflash.guides.resterror.exception.InvalidOperationException;
import dev.mflash.guides.resterror.exception.ResourceNotFoundException;
import dev.mflash.guides.resterror.persistence.BookRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/book")
public @RestController class BookController {

  private final BookRepository repository;

  public BookController(BookRepository repository) {
    this.repository = repository;
  }

  @PutMapping
  public Map<String, String> addBook(Book newBook) {
    repository.save(newBook);
    return Map.of("message", String.format("Save successful for %s", newBook.getTitle()));
  }

  @GetMapping
  public List<Book> getAllBooks() {
    List<Book> allBooks = List.of();
    repository.findAll().forEach(allBooks::add);
    return allBooks;
  }

  @GetMapping("/{id}")
  public Book getBookById(@PathVariable long id) {
    Optional<Book> book = repository.findById(id);

    if (book.isEmpty()) {
      throw new ResourceNotFoundException(String.format("No resource found for id (%s)", id));
    }

    return book.get();
  }

  @PatchMapping("/{id}")
  public Map<String, String> editBook(@PathVariable long id, Book editedBook) {
    final Optional<Book> saved = repository.findById(id);

    if (saved.isPresent()) {
      final Book savedBook = saved.get();
      final Book patchedBook = new Book();
      patchedBook.setId(savedBook.getId());
      patchedBook.setTitle(
          !editedBook.getTitle().equals(savedBook.getTitle()) ? editedBook.getTitle() : savedBook.getTitle());
      patchedBook.setAuthor(
          !editedBook.getAuthor().equals(savedBook.getAuthor()) ? editedBook.getAuthor() : savedBook.getAuthor());
      patchedBook.setGenre(
          !editedBook.getGenre().equals(savedBook.getGenre()) ? editedBook.getGenre() : savedBook.getGenre());
      repository.save(patchedBook);
    } else {
      throw new InvalidOperationException("Couldn't patch unrelated or non-existent records");
    }

    return Map.of("message", String.format("Patch successful for %s", editedBook.getTitle()));
  }

  @DeleteMapping("/{id}")
  public Map<String, String> deleteBook(@PathVariable long id) {
    if (repository.deleteById(id) < 1) {
      throw new InvalidOperationException("Couldn't delete a non-existent record");
    }

    return Map.of("message", String.format("Deletion successful for book with id: %s", id));
  }
}
