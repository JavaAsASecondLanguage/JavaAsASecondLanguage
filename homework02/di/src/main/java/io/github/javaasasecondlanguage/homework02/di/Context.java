package io.github.javaasasecondlanguage.homework02.di;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Context {

    private static Map<Class, Supplier<?>> dependenciesByClass;
    private static Map<String, Supplier<?>> dependenciesByQualifier;

    public Context() {
        Injector.registerContext(this);
        dependenciesByClass = new HashMap<>();
        dependenciesByQualifier = new HashMap<>();
    }

    public <T> Supplier<T> findBeanByClass(Class<T> clazz) {
        return (Supplier<T>) dependenciesByClass.get(clazz);
    }

    public <T> Supplier<T> findBeanByClassAndQualifier(String qualifier) {
        return (Supplier<T>) dependenciesByQualifier.get(qualifier);
    }

    public Context register(Class<?> clazz, Supplier<?> supplier) {
        dependenciesByClass.putIfAbsent(clazz, supplier);
        return this;
    }

    public Context register(String qualifier, Supplier<?> supplier) {
        dependenciesByQualifier.putIfAbsent(qualifier, supplier);
        return this;
    }
}
