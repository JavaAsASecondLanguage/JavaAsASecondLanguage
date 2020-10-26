package io.github.javaasasecondlanguage.flitter.util;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message + " not found");
    }
}
