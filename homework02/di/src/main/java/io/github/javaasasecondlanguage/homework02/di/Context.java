package io.github.javaasasecondlanguage.homework02.di;

import java.util.HashMap;
import java.util.function.Supplier;

import javax.naming.OperationNotSupportedException;

import com.sun.jdi.Type;

public class Context implements AutoCloseable {

    private static HashMap<String, Context> ctxContainer = new HashMap<String, Context>();
    
    public static Context getContext(String name) {
        if (name == null || name.isEmpty()) {
            name = "global";
        }
        var ctx = ctxContainer.getOrDefault(name, null);
        if (ctx == null) {
            ctx = new Context(name);
            ctxContainer.put(name, ctx);
            // possible copy registrations here
        }
        return ctx;
    }

    public static Context getContext() {
        return getContext("global");
    }

    private String name = null;
    private HashMap<String, Object> registry = new HashMap<String, Object>();

    private Context(String name) {
        this.name = name;
    }

    @Override
    public void close() {
        ctxContainer.remove(this.name);
        // clean up resouces
    }

    public <T> Context register(Supplier<T> supplier, String qualifier) {
        registry.put(qualifier, supplier);
        return this;
    }

    public <T> Context register(Supplier<T> supplier) {
        // empty string as a default qualifier
        return register(supplier, "");
    }

    public <T> T resolve(Class<T> clazz, String qualifier) throws OperationNotSupportedException {
        var entry = registry.getOrDefault(qualifier, null);
        if (entry == null) {
            throw new OperationNotSupportedException("registration not found: " + qualifier);
        }
        var supplier = (Supplier<T>) entry;
        if (supplier == null) {
            throw new OperationNotSupportedException("invalid registration: " + qualifier);
        }
        return supplier.get();
    }

    public <T> T resolve(Class<T> clazz) throws OperationNotSupportedException {
        // empty string as a default qualifier
        return resolve(clazz, "");
    }

    public <T> T tryResolve(Class<T> clazz, String qualifier) {
        try {
            var obj = (T) resolve(clazz, qualifier);
            return obj;
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getExplanation());
        }
        return null;
    }

    public <T> T tryResolve(Class<T> clazz) {
        return tryResolve(clazz, "");
    }
}
