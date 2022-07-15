package dev.mflash.guides.springfox3;

import org.springframework.data.annotation.Id;

import java.util.StringJoiner;

public class Note {

  private @Id long id;
  private String title;
  private String content;

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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public static Note parseNote(String line) {
    String[] text = line.split(",");
    Note note = new Note();
    note.setTitle(text[0]);
    note.setContent(text[1]);
    return note;
  }

 public  @Override String toString() {
    return new StringJoiner(", ", Note.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("content='" + content + "'")
        .toString();
  }
}
