package io.github.javaasasecondlanguage.flitter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response<T> {
    @JsonProperty("data")
    private T data;
    @JsonProperty("errorMessage")
    private String errorMessage;

    public Response(T data) {
        this.data = data;
        this.errorMessage = null;
    }

    public Response(T data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public Response(String errorMessage) {
        this.data = null;
        this.errorMessage = errorMessage;
    }
}
