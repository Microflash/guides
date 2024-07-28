package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface BookRepository extends CrudRepository<Book, Long> {

	Stream<Book> findAllByGenre(Genre genre);
}
