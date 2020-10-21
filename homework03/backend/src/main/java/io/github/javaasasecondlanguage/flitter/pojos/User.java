package io.github.javaasasecondlanguage.flitter.pojos;

import java.io.Serializable;
import java.util.UUID;

/**
 * Пользователь системы.
 */
public class User implements Serializable {
    /**
     * Наименование пользователя.
     */
    private String userName;

    /**
     * Идентификатор пользователя.
     */
    private UUID userToken;

    public User() {
    }

    public User(String userName, UUID userToken) {
        this.userName = userName;
        this.userToken = userToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UUID getUserToken() {
        return userToken;
    }

    public void setUserToken(UUID userToken) {
        this.userToken = userToken;
    }
}
