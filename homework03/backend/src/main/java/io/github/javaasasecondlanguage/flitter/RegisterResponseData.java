package io.github.javaasasecondlanguage.flitter;

class RegisterResponseData {
    public String userName;
    public String userToken;

    RegisterResponseData(String userName, String userToken) {
        this.userName = userName;
        this.userToken = userToken;
    }
}
