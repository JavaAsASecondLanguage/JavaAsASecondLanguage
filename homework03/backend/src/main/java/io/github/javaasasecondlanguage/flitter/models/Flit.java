package io.github.javaasasecondlanguage.flitter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Flit implements Comparable {
    public User user;

    @JsonProperty("content")
    public String content;

    public LocalDateTime createdAt = LocalDateTime.now();

    public Flit(User user, String content) {
        this.user = user;
        this.content = content;
    }

    @Override
    public int compareTo(Object o) {
        var another = (Flit)o;
        return createdAt.compareTo(another.createdAt);
    }

    @JsonProperty("userName")
    public String userName() {
        return user.name;
    }
}
