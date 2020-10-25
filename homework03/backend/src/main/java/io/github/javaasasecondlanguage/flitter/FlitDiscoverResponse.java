package io.github.javaasasecondlanguage.flitter;

import java.util.LinkedList;

class FlitDiscoverResponse {
    public LinkedList<FlitDiscoverPart> data;

    FlitDiscoverResponse(LinkedList<FlitDiscoverPart> data) {
        this.data = data;
    }
}
