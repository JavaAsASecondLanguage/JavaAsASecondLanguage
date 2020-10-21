package io.github.javaasasecondlanguage.flitter.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

/**
 * Сообщение.
 */
public class Flit {
    /**
     * Наименование пользователя - автора сообщения.
     */
    private String userName;

    /**
     * Содержание сообщения.
     */
    private String content;

    /**
     * Множество пользователей, прочитавших данное сообщение.
     */
    private final Set<UUID> seenBy;

    public Flit(String userName, String content) {
        this.userName = userName;
        this.content = content;
        seenBy = new HashSet<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonIgnore
    public Set<UUID> getSeenBy() {
        return seenBy;
    }
}
