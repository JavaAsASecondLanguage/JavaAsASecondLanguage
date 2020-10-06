package io.github.javaasasecondlanguage.homework02.di.exceptions;

public class UnresolvedDependenciesException extends RuntimeException {

    public UnresolvedDependenciesException() {
    }

    public UnresolvedDependenciesException(String message) {
        super(message);
    }

    public UnresolvedDependenciesException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnresolvedDependenciesException(Throwable cause) {
        super(cause);
    }

    public UnresolvedDependenciesException(String message,
                                           Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
