package io.github.javaasasecondlanguage.homework02.di.exceptions;

public class BeanNotFoundException extends RuntimeException {
    public BeanNotFoundException(Class<?> clazz, String qualifier) {
        super(String.format("Not found bean for class %s with qualifier %s", clazz, qualifier));
    }
}
