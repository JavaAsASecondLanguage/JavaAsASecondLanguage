package io.github.javaasasecondlanguage.flitter.dto;

public class AddFlitDto {

    String userToken;
    String content;

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
