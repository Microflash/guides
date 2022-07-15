package dev.mflash.guides.resterror.domain;

import org.springframework.data.annotation.Id;

import java.util.StringJoiner;

public class Book {

  private @Id long id;
  private String title;
  private String author;
  private Genre genre;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Genre getGenre() {
    return genre;
  }

  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  public @Override String toString() {
    return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("author='" + author + "'")
        .add("genre=" + genre)
        .toString();
  }
}
