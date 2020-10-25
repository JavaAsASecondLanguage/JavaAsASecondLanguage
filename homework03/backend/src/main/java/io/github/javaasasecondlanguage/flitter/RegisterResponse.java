package io.github.javaasasecondlanguage.flitter;

class RegisterResponse {
    public RegisterResponseData data;
    public String errorMessage;

    RegisterResponse(RegisterResponseData data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }
}
