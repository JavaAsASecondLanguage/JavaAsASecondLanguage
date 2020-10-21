package io.github.javaasasecondlanguage.flitter.pojos;

/**
 * Запрос на создание сообщения в приложении.
 */
public class FlitRegisterRequest {
    /**
     * Идентификатор пользователя - автора сообщения.
     */
    private String userToken;

    /**
     * Содержание сообщения.
     */
    private String content;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
