package dev.mflash.guides.jwtauth.security;

import org.springframework.data.annotation.Id;

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
    return "CustomUser{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", name='" + name + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
