package io.github.javaasasecondlanguage.flitter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "flits")
public class Flit {
  @Id
  @GeneratedValue
  @Column(name = "uuid")
  private UUID id;

  @Column(name = "content")
  private String content;

  @ManyToOne
  private User author;

  @Column(name = "creation_time")
  private LocalDateTime creationTime;

  public UUID getId() {
    return id;
  }

  public Flit setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getContent() {
    return content;
  }

  public Flit setContent(String content) {
    this.content = content;
    return this;
  }

  public User getAuthor() {
    return author;
  }

  public Flit setAuthor(User author) {
    this.author = author;
    return this;
  }

  public LocalDateTime getCreationTime() {
    return creationTime;
  }

  public Flit setCreationTime(LocalDateTime creationTime) {
    this.creationTime = creationTime;
    return this;
  }
}
