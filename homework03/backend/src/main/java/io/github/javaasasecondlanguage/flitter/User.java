package io.github.javaasasecondlanguage.flitter;

import java.util.UUID;

class User {
    String name;
    String token;

    User(String name) {
        this.name = name;
        this.token = UUID.randomUUID().toString();
    }

    String getName() {
        return this.name;
    }

    String getToken() {
        return this.token;
    }
}
