package io.github.javaasasecondlanguage.flitter.dto;

public class ResultOrErrorDto<T> {
  private final T data;
  private final String errorMessage;

  private ResultOrErrorDto(T data, String errorMessage) {
    this.data = data;
    this.errorMessage = errorMessage;
  }

  public T getData() {
    return data;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public static <T> ResultOrErrorDto<T> result(T data) {
    return new ResultOrErrorDto<>(data, null);
  }

  public static <T> ResultOrErrorDto<T> error(String errorMessage) {
    return new ResultOrErrorDto<>(null, errorMessage);
  }
}
