package io.github.javaasasecondlanguage.homework02.di.exceptions;

public class AmbigiousBeanQueryException extends RuntimeException {
    public AmbigiousBeanQueryException(Class<?> clazz, String qualifier) {
        super(String.format("More than one bean found for class %s with qualifier %s",
                clazz, qualifier));
    }
}
