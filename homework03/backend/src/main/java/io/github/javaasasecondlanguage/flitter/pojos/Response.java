package io.github.javaasasecondlanguage.flitter.pojos;

import java.io.Serializable;

/**
 * Ответ на запрос
 * @param <T> тип объекта данных.
 */
public class Response<T> implements Serializable {
    /**
     * Данные ответа на запрос.
     * Может быть null в случае возникновения ошибки во время исполнения запроса.
     */
    private T data;

    /**
     * Сообщение об ошибке исполнения запроса.
     * Может быть null в случае успешной обработки запроса.
     */
    private String errorMessage;

    public Response(T data) {
        this.data = data;
        this.errorMessage = null;
    }

    public Response(String errorMessage) {
        this.data = null;
        this.errorMessage = errorMessage;
    }

    public Response(T data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
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
