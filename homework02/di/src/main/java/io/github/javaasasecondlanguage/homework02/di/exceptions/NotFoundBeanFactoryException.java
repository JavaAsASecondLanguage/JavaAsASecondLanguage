package io.github.javaasasecondlanguage.homework02.di.exceptions;

public class NotFoundBeanFactoryException extends RuntimeException {

    public NotFoundBeanFactoryException() {
    }

    public NotFoundBeanFactoryException(String message) {
        super(message);
    }

    public NotFoundBeanFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundBeanFactoryException(Throwable cause) {
        super(cause);
    }

    public NotFoundBeanFactoryException(String message,
                                        Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
