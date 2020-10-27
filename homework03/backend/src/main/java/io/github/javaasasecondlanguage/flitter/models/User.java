package io.github.javaasasecondlanguage.flitter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.UUID;

public class User {
    @JsonProperty("userName")
    public String name;

    @JsonProperty("userToken")
    public String token;

    public HashSet<User> subscribedTo = new HashSet<>();

    public User(String name) {
        this.name = name;
        this.token = UUID.randomUUID().toString();
    }

    public void subscribe(User publisher) {
        subscribedTo.add(publisher);
    }

    public void unsubscribe(User publisher) {
        subscribedTo.remove(publisher);
    }

    public boolean subscribed(User user) {
        return subscribedTo.contains(user);
    }
}
