package io.github.javaasasecondlanguage.flitter;


public class Flit {

    public Flit(String name, String token, String text) {
        this.name = name;
        this.token = token;
        this.text = text;
    }

    private final String name;
    private final String token;
    private final String text;

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
