package io.github.javaasasecondlanguage.flitter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Flit {

    @JsonIgnore
    private String id;
    private String userName;
    @JsonIgnore
    private String userToken;
    private String flitContent;

    public Flit(String id, String userName, String userToken, String flitContent) {
        this.id = id;
        this.userName = userName;
        this.userToken = userToken;
        this.flitContent = flitContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFlitContent() {
        return flitContent;
    }

    public void setFlitContent(String flitContent) {
        this.flitContent = flitContent;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
