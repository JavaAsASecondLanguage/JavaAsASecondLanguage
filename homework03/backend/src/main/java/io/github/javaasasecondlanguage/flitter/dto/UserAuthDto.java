package io.github.javaasasecondlanguage.flitter.dto;

public class UserAuthDto {
  private final String userName;
  private final String userToken;

  public UserAuthDto(String userName, String userToken) {
    this.userName = userName;
    this.userToken = userToken;
  }

  public String getUserName() {
    return userName;
  }

  public String getUserToken() {
    return userToken;
  }
}
