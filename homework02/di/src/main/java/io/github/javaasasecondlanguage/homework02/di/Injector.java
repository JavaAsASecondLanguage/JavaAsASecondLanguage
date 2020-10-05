package io.github.javaasasecondlanguage.homework02.di;

public class Injector {
    public static <T> T inject(Class<T> clazz) {
        return inject(clazz, null);
    }

    public static <T> T inject(Class<T> clazz, String qualifier) {
        if (Context.getSingletonContext() == null) {
            throw new IllegalArgumentException("Global context is not created");
        }

        return Context.getSingletonContext().lookupInjection(clazz, qualifier);
    }
}
