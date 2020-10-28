package io.github.javaasasecondlanguage.flitter;

import java.util.List;

class RegisterRequest {
    public String userName;
}

class FlitAddRequest {
    public String userToken;
    public String content;
}

class SubscribeRequest {
    public String subscriberToken;
    public String publisherName;
}

class StringsResponse {
    public List<String> data;

    public StringsResponse(List<String> userNames) {
        this.data = userNames;
    }
}

class SingleStringResponse {
    public String data;

    public SingleStringResponse(String data) {
        this.data = data;
    }
}

class RegisterUser {
    public String userName;
    public String userToken;

    public RegisterUser(String userName, String userToken) {
        this.userName = userName;
        this.userToken = userToken;
    }
}

class ErrorResponse {
    public RegisterUser data;
    public String errorMessage;

    public ErrorResponse(RegisterUser data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }
}

class UserFlitData {
    public String userName;
    public String content;

    public UserFlitData(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }
}

class UserFlitResponse {
    public List<UserFlitData> data;

    public UserFlitResponse(List<UserFlitData> data) {
        this.data = data;
    }
}

