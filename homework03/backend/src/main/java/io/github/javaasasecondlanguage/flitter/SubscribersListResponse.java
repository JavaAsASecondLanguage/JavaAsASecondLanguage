package io.github.javaasasecondlanguage.flitter;

import java.util.List;

class SubscribersListResponse {
    public List<String> data;
    public String errorMessage;

    SubscribersListResponse(List<String> data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }
}
