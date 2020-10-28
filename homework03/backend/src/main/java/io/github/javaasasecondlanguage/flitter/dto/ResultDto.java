package io.github.javaasasecondlanguage.flitter.dto;

public class ResultDto<T> {
  private final T data;

  public ResultDto(T data) {
    this.data = data;
  }

  public T getData() {
    return data;
  }

  public static <T> ResultDto<T> of(T data) {
    return new ResultDto<>(data);
  }
}
