package io.github.javaasasecondlanguage.flitter.dto;

public class UserDto {
  private String userName;

  public UserDto() {
  }

  public UserDto(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }
}
