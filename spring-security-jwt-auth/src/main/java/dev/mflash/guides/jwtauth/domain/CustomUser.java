package dev.mflash.guides.jwtauth.domain;

import org.springframework.data.annotation.Id;

import java.util.StringJoiner;

public class CustomUser {

  private @Id int id;
  private String email;
  private String name;
  private String password;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public @Override String toString() {
    return new StringJoiner(", ", CustomUser.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("email='" + email + "'")
        .add("name='" + name + "'")
        .add("password='" + password + "'")
        .toString();
  }
}
