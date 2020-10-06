package io.github.javaasasecondlanguage.homework02.di;

import java.util.*;

public class Context {

    private static final Map<String, List<Object>> contexts = new HashMap<>();

    public <T> Context register(T object, String qualifier) {
        String q = qualifier == null ? "" : qualifier;
        contexts.putIfAbsent(q, new ArrayList<>());
        contexts.get(q).add(object);
        return this;
    }

    public <T> Context register(int object, String qualifier) {
        register(Integer.valueOf(object), qualifier);
        return this;
    }

    public <T> Context register(T object) {
        register(object, "");
        return this;
    }

    public static <T> T getContext(Class<T> clazz, String qualifier) {
        if (!contexts.containsKey(qualifier)) {
            throw new RuntimeException("Context '"
                    + (qualifier == null ? "" : qualifier) + "' not register");
        }

        final T t = (T) contexts.get(qualifier)
                .stream()
                .filter(o -> clazz.equals(o.getClass()) || clazz.isInstance(o))
                .findFirst().get();

        return t;
    }

    public static <T> T getContext(Class<T> clazz) {
        return getContext(clazz, "");
    }

    public static void clear() {
        contexts.clear();
    }

}
