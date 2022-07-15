package dev.mflash.guides.java.deepcopy.domain;

public class Author implements Copyable<Author> {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Author copy() {
    final Author newAuthor = new Author();
    newAuthor.setName(name);
    return newAuthor;
  }
}
