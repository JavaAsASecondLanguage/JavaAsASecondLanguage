package io.github.javaasasecondlanguage.flitter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Flit {

    @JsonIgnore
    private String id;
    private String userName;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


}
