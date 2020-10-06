package io.github.javaasasecondlanguage.homework02.di.exceptions;

public class BeanAlreadyDefinedException extends RuntimeException {
    public BeanAlreadyDefinedException(Class<?> clazz, String qualifier) {
        super(String.format("Bean %s with qualifier %s already registered", clazz, qualifier));
    }
}
