package io.github.javaasasecondlanguage.homework02.di;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Context {
    static List<Object> contextObjects;
    static HashMap<String, Object> contextQualifierObjects;

    public Context() {
        contextObjects = new ArrayList<>();
        contextQualifierObjects = new HashMap<>();
    }

    public <T> Context register(T object, String qualifier) {
        if (object == null) {
            throw new IllegalArgumentException("ERROR: null object");
        }
        if (qualifier == null) {
            throw new IllegalArgumentException("ERROR: null qualifier");
        }
        contextQualifierObjects.put(qualifier, object);
        register(object);
        return this;
    }

    public <T> Context register(T object) {
        if (object == null) {
            throw new IllegalArgumentException("ERROR: null object");
        }
        int tmp = contextObjects.indexOf(object);
        if (tmp != -1) {
            contextObjects.remove(tmp);
        }
        contextObjects.add(object);
        return this;
    }
}
