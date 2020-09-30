package io.github.javaasasecondlanguage.homework02.di;

import java.util.HashMap;

public class Context {
    private HashMap<String, Object> objects = new HashMap<>();
    private HashMap<Class, Object> classes = new HashMap<>();
    private static Context instance;
    private Context () {};

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    public <T> Context register(T object, String qualifier) {
        objects.put(qualifier, object);
        return this;
    }

    public <T> Context register(T object) {
        var clazz = object.getClass();

        while (!clazz.equals(Object.class)) {
            classes.put(clazz, object);

            for (var i : clazz.getInterfaces()) {
                classes.put(i, object);
            }

            clazz = clazz.getSuperclass();
        }

        return this;
    }

    public Object findByQualifier(String qualifier) {
        return objects.get(qualifier);
    }

    public Object findByClass(Class clazz) {
        return classes.get(clazz);
    }

    public void clear() {
        classes.clear();
        objects.clear();
    }
}
