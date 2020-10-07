package io.github.javaasasecondlanguage.homework02.di;

import java.util.List;
import java.util.stream.Collectors;

public class Injector<T> {

    public static <T> T inject(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("ERROR: null clazz");
        }
        List<Object> targetObjects = Context.contextObjects
            .stream()
            .filter(o -> clazz.isAssignableFrom(o.getClass()))
            .collect(Collectors.toList());
        if (targetObjects.size() > 1) {
            throw new RuntimeException("ERROR: context objects conflict");
        } else if (targetObjects.size() == 0) {
            throw new RuntimeException("ERROR: unknown object");
        } else {
            return (T) targetObjects.get(0);
        }
    }

    public static <T> T inject(Class<T> clazz, String qualifier) {
        if (clazz == null) {
            throw new IllegalArgumentException("ERROR: null clazz");
        }
        if (qualifier == null) {
            throw new IllegalArgumentException("ERROR: null qualifier");
        }
        Object targetObject = Context.contextQualifierObjects.get(qualifier);
        if (!clazz.isAssignableFrom(targetObject.getClass())) {
            throw new RuntimeException("ERROR: Inappropriate class accotiated with qualifier");
        }
        return (T) targetObject;
    }
}
