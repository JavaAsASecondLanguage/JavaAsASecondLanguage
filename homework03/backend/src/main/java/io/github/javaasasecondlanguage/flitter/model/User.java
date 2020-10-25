package io.github.javaasasecondlanguage.flitter.model;

public class User {

    private String userToken;
    private String userName;

    public User(String userToken, String userName) {
        this.userToken = userToken;
        this.userName = userName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
