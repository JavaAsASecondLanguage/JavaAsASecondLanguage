package io.github.javaasasecondlanguage.homework02.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.function.Supplier;

public class Context implements AutoCloseable {

    static class RegistrationEntry<T> implements AutoCloseable {
        private T instance = null;
        private Class<T> clazz = null;
        private Supplier<T> factory = null;
        private Boolean resolved = false;
        private String qualifier = "";

        public RegistrationEntry(Class<T> clazz, T instance, String qualifier) {
            this.clazz = clazz;
            this.instance = instance;
            this.qualifier = qualifier;
            this.resolved = true;
        }

        public RegistrationEntry(Class<T> clazz, Supplier<T> factory, String qualifier) {
            this.clazz = clazz;
            this.factory = factory;
            this.qualifier = qualifier;
        }

        @Override
        public void close() throws Exception {
            if (Boolean.FALSE.equals(this.resolved)) {
                return;
            }
            if (this.clazz.isAssignableFrom(AutoCloseable.class)) {
                return;
            }
            ((AutoCloseable) this.instance).close();
        }

        public T resolve() {
            if (Boolean.FALSE.equals(this.resolved)) {
                this.instance = this.factory.get();
                this.resolved = (this.instance != null);
            }
            return this.instance;
        }
        
        public Boolean isResolved() {
            return this.resolved;
        }

        public String getQualifier() {
            return this.qualifier;
        }

        public Class<T> getRegisteredClass() {
            return this.clazz;
        }
    }

    public static final String ROOT_SCOPE = "root";
    public static final String DEFAULT_QUALIFIER = "default";

    private static final Context rootScope = new Context(ROOT_SCOPE, null);

    public static Context getRoot() {
        return rootScope;
    }

    public static Context getResolutionScope(String name) {
        if (name == null || name.isEmpty()) {
            name = ROOT_SCOPE;
        }
        if (ROOT_SCOPE.equals(name)) {
            return rootScope;
        }
        return rootScope.getScope(name);
    }

    private String name = null;
    private Context parent = null;
    private final HashMap<String, Context> scopes = new HashMap<>();
    private final HashMap<Class<?>, HashMap<String, RegistrationEntry<?>>>
            registry = new HashMap<>();

    private Context() {
    }

    private Context(String name, Context parent) {
        this.name = name;
        if (parent != null) {
            this.parent = parent;
            parent.scopes.put(name, this);
        }
    }

    public Context getScope(String name) {
        Context child = this.scopes.getOrDefault(name, null);
        if (child != null) {
            return child;
        }
        for (var scope : this.scopes.values()) {
            child = scope.getScope(name);
            if (child != null) {
                return child;
            }            
        }
        return null;
    }
    
    public Context openScope(String name) {
        var child = this.scopes.getOrDefault(name, null);
        if (child == null) {
            child = new Context(name, this);
            this.scopes.put(name, child);
        }
        return child;
    }

    @Override
    public void close() throws Exception {
        if (parent != null) {
            parent.scopes.remove(this.name);
        }

        for (var childScope : this.scopes.values()) {
            try {
                childScope.close();
            } catch (Exception ex) {
                handleException(ex);
                throw ex;
            }
        }

        var it = this.registry.values().stream()
                .flatMap(cr -> cr.values().stream())
                .iterator();
        while (it.hasNext()) {
            try {
                it.next().close();
            } catch (Exception ex) {
                handleException(ex);
                throw ex;
            }
        }

    }

    private void handleException(Exception ex) {
        // TODO: Exception handling
    }
    
    private <T> T factoryByDefaultConstructor(Class<T> clazz) {
        try {
            Constructor<T> defaultCtor = null;
            for (var ctor : clazz.getDeclaredConstructors()) {
                if (ctor.getParameterCount() == 0) {
                    defaultCtor = (Constructor<T>) ctor;
                    break;
                }
            }
            if (defaultCtor != null) {
                return defaultCtor.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException 
                | InvocationTargetException ex) {
            handleException(ex);
            throw new UnsupportedOperationException(ex.getMessage());
        }
        return null;
    }

    private <T> T selectFactory(Class<T> clazz) {
        // TODO: Factory selection policy
        return factoryByDefaultConstructor(clazz);
    }

    private HashMap<String, RegistrationEntry<?>> getClassEntry(Class<?> clazz) {
        var classEntry = registry.getOrDefault(clazz, null);
        if (classEntry == null) {
            classEntry = new HashMap<>();
            registry.put(clazz, classEntry);
        }
        return classEntry;
    }

    public <T> Context register(Class<T> clazz, Supplier<T> supplier, String qualifier) {
        if (qualifier == null) {
            qualifier = DEFAULT_QUALIFIER;
        }
        var clazzEntry = this.getClassEntry(clazz);
        var entry = clazzEntry.getOrDefault(qualifier, null);
        if (entry != null) {
            // TODO: Duplicate registration policy
        } else {
            entry = new RegistrationEntry<>(clazz, supplier, qualifier);
            clazzEntry.put(qualifier, entry);
        }
        return this;
    }

    public <T> Context register(Class<T> clazz, Supplier<T> supplier) {
        return register(clazz, supplier, null);
    }

    public <T> Context register(Class<T> clazz, String qualifier) {
        Supplier<T> factory = () -> selectFactory(clazz);
        return register(clazz, factory, qualifier);
    }
    
    public <T> Context register(Class<T> clazz) {
        Supplier<T> factory = () -> selectFactory(clazz);
        return register(clazz, factory, null);
    }
    
    public <T> Context register(T instance, String qualifier) {
        if (qualifier == null) {
            qualifier = DEFAULT_QUALIFIER;
        }
        var clazz = (Class<T>) instance.getClass();
        var clazzEntry = this.getClassEntry(clazz);
        var entry = clazzEntry.getOrDefault(qualifier, null);
        if (entry != null) {
            // TODO: Duplicate registration policy
        } else {
            entry = new RegistrationEntry<>(clazz, instance, qualifier);
            clazzEntry.put(qualifier, entry);
        }
        return this;
    }
    
    public <T> Context register(T instance) {
        return register(instance, null);
    }

    public Context register(Double value, String qualifier) {
        return register(Double.class, () -> value, qualifier);
    }

    public Context register(Long value, String qualifier) {
        return register(Long.class, () -> value, qualifier);
    }

    public Context register(Integer value, String qualifier) {
        return register(Integer.class, () -> value, qualifier);
    }

    public Context register(Boolean value, String qualifier) {
        return register(Boolean.class, () -> value, qualifier);
    }

    private RegistrationEntry<?> findByClass(Class<?> clazz, String qualifier) {
        var clazzEntry = registry.getOrDefault(clazz, null);
        if (clazzEntry == null) {
            // TODO: Resolution policy
            return null;
        }
        return clazzEntry.getOrDefault(qualifier, null);
    }

    private RegistrationEntry<?> findByInterface(Class<?> clazz, String qualifier) {
        var entry = registry.values().stream()
            .flatMap(r -> r.values().stream())
            .filter(r -> clazz.isAssignableFrom(r.getRegisteredClass()))
            .filter(r -> r.getQualifier().equals(qualifier))
            .findFirst();
        return entry.orElse(null);
    }

    public <T> T resolve(Class<T> clazz, String qualifier) {
        if (qualifier == null) {
            qualifier = DEFAULT_QUALIFIER;
        }

        // TODO: Resolution policy
        var entry = findByClass(clazz, qualifier);
        if (entry != null) {
            return (T) entry.resolve();
        }
        entry = findByInterface(clazz, qualifier);
        if (entry != null) {
            return (T) entry.resolve();
        }
        return null;
    }

    public <T> T resolve(Class<T> clazz) {
        return resolve(clazz, null);
    }
}
