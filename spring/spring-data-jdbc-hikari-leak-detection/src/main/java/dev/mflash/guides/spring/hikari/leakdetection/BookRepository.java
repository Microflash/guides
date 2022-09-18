package dev.mflash.guides.spring.hikari.leakdetection;

import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface BookRepository extends CrudRepository<Book, Long> {

	Stream<Book> findAllByGenre(Genre genre);
}
