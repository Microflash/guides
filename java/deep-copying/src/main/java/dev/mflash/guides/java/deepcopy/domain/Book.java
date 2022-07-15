package dev.mflash.guides.java.deepcopy.domain;

public class Book implements Copyable<Book> {

  private String title;
  private Author author;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  @Override
  public Book copy() {
    final Book newBook = new Book();
    newBook.setAuthor(author.copy());
    newBook.setTitle(title);
    return newBook;
  }
}
