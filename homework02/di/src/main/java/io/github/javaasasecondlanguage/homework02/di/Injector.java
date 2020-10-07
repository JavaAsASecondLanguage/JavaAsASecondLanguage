package io.github.javaasasecondlanguage.homework02.di;

import io.github.javaasasecondlanguage.homework02.di.Context;

public class Injector {

    public static <T> T inject(Class<T> clazz) {
        return Context.getContext().tryResolve(clazz);
    }

    public static <T> T inject(Class<T> clazz, String qualifier) {
        return Context.getContext().tryResolve(clazz, qualifier);
    }
}
