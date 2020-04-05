package dev.mflash.guides.pagila.domain;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.OffsetDateTime;

public class Actor {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Id int actorId;
  private String firstName;
  private String lastName;
  private OffsetDateTime lastUpdate;

  public int getActorId() {
    return actorId;
  }

  public void setActorId(int actorId) {
    this.actorId = actorId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public OffsetDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(OffsetDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
