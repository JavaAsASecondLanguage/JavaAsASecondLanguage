package io.github.javaasasecondlanguage.homework02.di;

import io.github.javaasasecondlanguage.homework02.di.exceptions.UnknownContextException;

import java.util.LinkedHashMap;

public class ContextRegistry {
    static final String DEFAULT_CONTEXT_NAME = "DEFAULT_CONTEXT";
    private static final ThreadLocal<String> currentThreadContextName = new ThreadLocal<>();

    // LinkedHashMap for preserving insertion order
    private static final LinkedHashMap<String, Context> CONTEXT_INSTANCES = new LinkedHashMap<>();

    public static synchronized void registerContext(Context context) {
        var contextName = context.getName();
        if (CONTEXT_INSTANCES.containsKey(contextName)) {
            throw new RuntimeException(
                    String.format("Context with name %s already defined", contextName));
        }
        if (CONTEXT_INSTANCES.size() == 0) {
            currentThreadContextName.set(contextName);
        }
        CONTEXT_INSTANCES.put(contextName, context);
    }

    public static Context getContext(String contextName) {
        if (!CONTEXT_INSTANCES.containsKey(contextName)) {
            throw new UnknownContextException(contextName);
        }
        return CONTEXT_INSTANCES.get(contextName);
    }

    public static Context getContext() {
        if (CONTEXT_INSTANCES.isEmpty()) {
            throw new RuntimeException("Context not defined");
        }
        if (currentThreadContextName.get() == null) {
            currentThreadContextName.set(CONTEXT_INSTANCES.keySet().iterator().next());
        }
        return getContext(currentThreadContextName.get());
    }

    public static synchronized Context setDefaultContext(String contextName) {
        if (!CONTEXT_INSTANCES.containsKey(contextName)) {
            throw new UnknownContextException(contextName);
        }
        currentThreadContextName.set(contextName);
        return CONTEXT_INSTANCES.get(contextName);
    }

    public static synchronized void clearContext() {
        currentThreadContextName.remove();
        CONTEXT_INSTANCES.clear();
    }
}
