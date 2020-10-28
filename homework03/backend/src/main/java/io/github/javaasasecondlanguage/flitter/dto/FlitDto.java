package io.github.javaasasecondlanguage.flitter.dto;

public class FlitDto {
  private String id;
  private String userName;
  private String content;

  public FlitDto() {
  }

  public FlitDto(String id, String userName, String content) {
    this.id = id;
    this.userName = userName;
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public String getUserName() {
    return userName;
  }
}
