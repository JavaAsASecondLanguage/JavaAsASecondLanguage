package io.github.javaasasecondlanguage.homework02.di.exceptions;

public class UnknownContextException extends RuntimeException {
    public UnknownContextException(String contextName) {
        super(String.format("Unknown context %s", contextName));
    }
}
