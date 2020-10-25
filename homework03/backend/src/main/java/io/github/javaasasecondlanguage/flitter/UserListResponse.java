package io.github.javaasasecondlanguage.flitter;

import java.util.LinkedList;

class UserListResponse {
    public LinkedList<String> data;

    UserListResponse(LinkedList<String> userNames) {
        this.data = userNames;
    }
}
