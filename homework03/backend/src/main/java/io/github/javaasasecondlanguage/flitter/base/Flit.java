package io.github.javaasasecondlanguage.flitter.base;

public class Flit {
    private User user;
    private String content;

    public Flit(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public FlitGet get() {
        return new FlitGet(user.getUserName(), content);
    }

}
