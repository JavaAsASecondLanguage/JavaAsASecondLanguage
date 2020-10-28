package io.github.javaasasecondlanguage.flitter.dto;

public class PostFlitDto {
  private String userToken;
  private String content;

  public PostFlitDto() {
  }

  public PostFlitDto(String userToken, String content) {
    this.userToken = userToken;
    this.content = content;
  }

  public String getUserToken() {
    return userToken;
  }

  public String getContent() {
    return content;
  }
}
