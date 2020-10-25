package io.github.javaasasecondlanguage.flitter;

import java.util.LinkedList;

class FlitListResponse {
    public LinkedList<FlitListPart> data;
    public String errorMessage;

    FlitListResponse(LinkedList<FlitListPart> data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }
}
