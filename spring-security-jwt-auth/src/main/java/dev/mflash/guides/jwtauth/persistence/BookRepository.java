package dev.mflash.guides.jwtauth.persistence;

import dev.mflash.guides.jwtauth.domain.Book;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends CrudRepository<Book, Long> {

  @Modifying
  @Query("delete from book where id = :id")
  int deleteById(@Param("id") long id);
}
