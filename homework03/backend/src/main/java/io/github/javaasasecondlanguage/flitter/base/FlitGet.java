package io.github.javaasasecondlanguage.flitter.base;

/**
 * Simple Flit dataclass for JSON get flit
 */
public class FlitGet {
    private String userName;
    private String content;

    public FlitGet(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public String getUserName() { return userName; }

    public String getContent() { return content; }

}
