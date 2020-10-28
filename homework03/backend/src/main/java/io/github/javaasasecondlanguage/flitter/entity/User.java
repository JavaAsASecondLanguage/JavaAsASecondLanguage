package io.github.javaasasecondlanguage.flitter.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "users")
public class User {
  @Id
  @Column(name = "user_name")
  private String userName;

  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "user_token")
  private UUID userToken;

  public String getUserName() {
    return userName;
  }

  public User setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public UUID getUserToken() {
    return userToken;
  }

  public User setUserToken(UUID userToken) {
    this.userToken = userToken;
    return this;
  }
}
