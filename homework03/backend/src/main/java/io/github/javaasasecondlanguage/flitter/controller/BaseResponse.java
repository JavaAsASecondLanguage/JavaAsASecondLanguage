package io.github.javaasasecondlanguage.flitter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseResponse<T> {

    private T data;
    private String errorMessage;

    private BaseResponse() {

    }

    private BaseResponse(T data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok(T data) {
        var response = new BaseResponse<T>(data, null);
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok() {
        return ok(null);
    }

    public static ResponseEntity<?> error(String errorMessage) {
        var response = new BaseResponse<Object>(null, errorMessage);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<?> badRequest() {
        var response = new BaseResponse<Object>(null,"Bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public static ResponseEntity<?> notFound(String msg) {
        var response = new BaseResponse<Object>(null, msg);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    public static ResponseEntity<?> alreadyExists(String msg) {
        var response = new BaseResponse<Object>(null, msg);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
