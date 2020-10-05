package io.github.javaasasecondlanguage.homework02.di;

import java.util.*;
import java.util.stream.Collectors;

public class Context {
    private final Map<String, List<Object>> qualifierToObjects = new HashMap<>();

    public Context() {
        Injector.init(this);
    }

    public <T> Context register(T object) {
        return register(object, Injector.DEFAULT_QUALIFIER);
    }

    public <T> Context register(T object, String qualifier) {
        qualifierToObjects.computeIfAbsent(qualifier, k -> new ArrayList<>()).add(object);
        return this;
    }

    public Object find(Class<?> clazz, String qualifier) {
        var found = qualifierToObjects.get(qualifier).stream()
                .filter(clazz::isInstance)
                .collect(Collectors.toList());
        var foundSize = found.size();
        if (foundSize > 1) {
            throw new RuntimeException("Found more than 1 satisfying dependency!");
        }
        if (foundSize < 1) {
            throw new RuntimeException("Failed to find satisfying dependency!");
        }
        return found.get(0);
    }
}
