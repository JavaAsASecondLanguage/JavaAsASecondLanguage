package io.github.javaasasecondlanguage.homework02.di;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Context {

    private final Map<Class<?>, Map<String, Object>> store;

    public Context() {
        this.store = new HashMap<>();
        Injector.setContext(this);
    }

    public <T> Context register(T object, String qualifier) {
        Class<?> clazz = object.getClass();
        while (!clazz.equals(Object.class)) {
            registerInterfaces(clazz, object, qualifier);
            clazz = clazz.getSuperclass();
        }
        return this;
    }

    public <T> Context register(T object) {
        return register(object, null);
    }

    private void register(Class<?> clazz, Object object, String qualifier) {
        store.putIfAbsent(clazz, new HashMap<>());
        store.get(clazz).put(qualifier, object);
    }

    private void registerInterfaces(Class<?> clazz, Object object, String qualifier) {
        Class<?>[] rootInterface;
        register(clazz, object, qualifier);
        Class<?>[] interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            register(interfaces[i], object, qualifier);
            rootInterface = interfaces[i].getInterfaces();
            while (rootInterface.length > 0) {
                register(rootInterface[0], object, qualifier);
                rootInterface = rootInterface[0].getInterfaces();
            }
        }
    }

    public <T> T getBean(Class<T> clazz, String qualifier) {
        return (T) store.getOrDefault(clazz, Collections.emptyMap()).get(qualifier);
    }
}
