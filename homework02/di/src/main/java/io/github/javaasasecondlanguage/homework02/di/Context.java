package io.github.javaasasecondlanguage.homework02.di;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;


/**
 * Context for registration of injected dependencies
 */

public class Context {

    private static Map<Class<?>, HashMap<String, Object>> dependencies;
    static Context singleton = null;

    public Context() {
        if (singleton != null) {
            return;
        }
        singleton = this;
        this.dependencies = new HashMap<>();
    }

    /**
     * Add dependency to map
     *
     * @param clazz object class
     * @param obj registered dependency
     * @param qualifier qualifier of dependency
     */
    void createInjection(Class<?> clazz, Object obj, String qualifier) {
        HashMap<String, Object> node = null;

        // check class in hashmap
        if (this.dependencies.containsKey(clazz)) {
            //get dependency for given class if it exists
            node = this.dependencies.get(clazz);
        } else {
            // if clazz not in Map
            node = new HashMap<>();
            this.dependencies.put(clazz, node);
        }

        // check qualifier in map
        if (node.containsKey(qualifier)) {
            throw new KeyAlreadyExistsException();
        }
        node.put(qualifier, obj);
    }

    /**
     * Register dependecnies with qualifier
     *
     * @param object registered dependency
     * @param qualifier qualifier of dependency
     * @return context
     */
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

    /**
     * Register dependencies without qualifier
     *
     * @param object registered dependency
     * @return context
     */
    public <T> Context register(T object) {
        return register(object, null);
    }

    /**
     * Find dependency in map
     *
     * @param clazz object class
     * @param qualifier qualifier of dependency
     * @return object to injected class
     */
    public <T> T findDependency(Class<T> clazz, String qualifier) {
        HashMap<String, Object> dependency = this.dependencies.get(clazz);
        T object = null;

        if (dependency != null) {
            object = (T) dependency.get(qualifier);
        }
        return object;
    }

    /**
     * Get unique class
     *
     * @return singleton
     */
    public static Context getSingleton() {
        return singleton;
    }
}
