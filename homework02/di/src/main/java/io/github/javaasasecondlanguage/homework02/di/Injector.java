package io.github.javaasasecondlanguage.homework02.di;

public class Injector {
    public static <T> T inject(Class<T> clazz) {
        return ContextRegistry.getContext().getBean(clazz);
    }

    public static <T> T inject(Class<T> clazz, String qualifier) {
        return ContextRegistry.getContext().getBean(clazz, qualifier);
    }
}
