package io.github.javaasasecondlanguage.flitter.base;

import java.util.UUID;

public class User implements Comparable<User> {
    private String userName;
    private String userToken;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public User(String userName) {
        this.userName = userName;
        userToken = UUID.randomUUID().toString();
    }

    public User(String userName, String userToken) {
        this.userName = userName;
        this.userToken = userToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserToken() {
        return userToken;
    }

    @Override
    public int compareTo(User o) {
        return userToken.compareTo(o.getUserToken());
    }
}
