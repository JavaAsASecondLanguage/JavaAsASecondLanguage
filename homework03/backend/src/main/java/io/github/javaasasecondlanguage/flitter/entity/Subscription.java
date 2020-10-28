package io.github.javaasasecondlanguage.flitter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity(name = "subsctiprion")
public class Subscription {
  @Id
  @GeneratedValue
  @Column(name = "subscription_id", columnDefinition = "uuid")
  private UUID id;


  // hate many to many performance
  @ManyToOne
  private User publisher;

  @ManyToOne
  private User subscriber;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public User getPublisher() {
    return publisher;
  }

  public Subscription setPublisher(User publisher) {
    this.publisher = publisher;
    return this;
  }

  public User getSubscriber() {
    return subscriber;
  }

  public Subscription setSubscriber(User subscriber) {
    this.subscriber = subscriber;
    return this;
  }
}
