package io.github.javaasasecondlanguage.flitter.pojos;

import java.io.Serializable;

/**
 * Запрос на регистрацию пользователя в системе.
 */
public class UserRegisterRequest implements Serializable {
    /**
     * Наименование пользователя.
     */
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
