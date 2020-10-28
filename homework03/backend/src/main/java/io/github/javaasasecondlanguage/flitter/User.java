package io.github.javaasasecondlanguage.flitter;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final String token;
    private List<String> followers = new ArrayList<>();
    private List<String> subscriptions = new ArrayList<>();

    public User(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public List<String> getSubscriptions() {
        return subscriptions;
    }
}
