package com.example;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

//import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

public interface BookRepository extends CrudRepository<Book, UUID> {

	@Query("select * from book where array_contains(genre, :genre)")
	Stream<Book> findAllByGenre(String genre);
//	Collection<Book> findAllByGenre(String genre);
}
