package io.github.javaasasecondlanguage.homework02.di;

/**
 * Agent to inject dependencies
 */

public class Injector {

    /**
     * Get object of injected class
     * Inject class without qualifier
     *
     * @param clazz object class
     * @return object of injected class
     */
    public static <T> T inject(Class<T> clazz) {
        return inject(clazz, null);
    }

    /**
     * Get object of injected class with qualifier
     *
     * @param clazz object class
     * @param qualifier qualifier of dependency
     * @return object of injected class
     */
    public static <T> T inject(Class<T> clazz, String qualifier) {
        if (Context.getSingleton() == null) {
            throw new IllegalArgumentException();
        }
        return Context.getSingleton().findDependency(clazz, qualifier);
    }
}
