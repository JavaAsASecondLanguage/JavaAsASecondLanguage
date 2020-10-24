package io.github.javaasasecondlanguage.flitter.base;

public class Response<T> {
    public T data;
    public String errorMessage;

    public Response(T data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public Response(T data) {
        this.data = data;
        errorMessage = null;
    }

}
