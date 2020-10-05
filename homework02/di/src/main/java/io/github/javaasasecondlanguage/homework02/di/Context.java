package io.github.javaasasecondlanguage.homework02.di;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;

public class Context {
    static Context singletonContext = null;
    HashMap<Class<?>, HashMap<String, Object>> injectedObjects;

    void createInjection(Class<?> curClass, Object obj, String qualifier) {
        HashMap<String, Object> node = null;

        if (this.injectedObjects.containsKey(curClass)) {
            node = this.injectedObjects.get(curClass);
        } else {
            node = new HashMap<>();
            this.injectedObjects.put(curClass, node);
        }

        if (node.containsKey(qualifier)) {
            throw new KeyAlreadyExistsException("(class: " + curClass + ", qual: " + qualifier
                    + ") already set");
        }
        node.put(qualifier, obj);
    }

    public <T> Context register(T object, String qualifier) throws KeyAlreadyExistsException {
        Class<?> objectClass = object.getClass();
        Class<?> []interfaces;

        while (!objectClass.equals(Object.class)) {
            createInjection(objectClass, object, qualifier);

            interfaces = objectClass.getInterfaces();
            while (interfaces.length == 1) {
                createInjection(interfaces[0], object, qualifier);
                interfaces = interfaces[0].getInterfaces();
            }

            objectClass = objectClass.getSuperclass();
        }

        return this;
    }

    public <T> Context register(T object) throws KeyAlreadyExistsException {
        return this.register(object, null);
    }

    public <T> T lookupInjection(Class<T> clazz, String qualifier) {
        HashMap<String, Object> injection = this.injectedObjects.get(clazz);
        T object = null;

        if (injection != null) {
            object = (T) injection.get(qualifier);
        }
        return object;
    }

    public static Context getSingletonContext() {
        return singletonContext;
    }

    public Context() {
        if (singletonContext != null) {
            return;
        }

        singletonContext = this;
        this.injectedObjects = new HashMap<>();
    }
}
