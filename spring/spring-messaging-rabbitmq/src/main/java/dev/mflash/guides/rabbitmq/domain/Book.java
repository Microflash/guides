package dev.mflash.guides.rabbitmq.domain;

public class Book {

  private final String isbn;
  private final String title;
  private final String author;

  public Book(BookBuilder builder) {
    this.isbn = builder.isbn;
    this.title = builder.title;
    this.author = builder.author;
  }

  public String getIsbn() {
    return isbn;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public @Override String toString() {
    return "Book{" +
        "isbn='" + isbn + '\'' +
        ", title='" + title + '\'' +
        ", author='" + author + '\'' +
        '}';
  }

  public static class BookBuilder {

    private String isbn;
    private String title;
    private String author;

    public BookBuilder isbn(String isbn) {
      this.isbn = isbn;
      return this;
    }

    public BookBuilder title(String title) {
      this.title = title;
      return this;
    }

    public BookBuilder author(String author) {
      this.author = author;
      return this;
    }

    public Book build() {
      return new Book(this);
    }
  }
}
