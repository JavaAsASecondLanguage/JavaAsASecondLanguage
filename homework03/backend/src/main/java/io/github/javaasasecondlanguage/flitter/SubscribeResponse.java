package io.github.javaasasecondlanguage.flitter;

class SubscribeResponse {
    public String data;
    public String errorString;

    SubscribeResponse(String data, String errorString) {
        this.data = data;
        this.errorString = errorString;
    }
}
