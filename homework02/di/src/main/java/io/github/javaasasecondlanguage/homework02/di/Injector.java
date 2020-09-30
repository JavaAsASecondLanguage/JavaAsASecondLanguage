package io.github.javaasasecondlanguage.homework02.di;

import io.github.javaasasecondlanguage.homework02.di.Context;

public class Injector {
    public static <T> T inject(Class<T> clazz) {
        var context = Context.getInstance();
        return (T)context.findByClass(clazz);
    }

    public static <T> T inject(Class<T> clazz, String qualifier) {
        var context = Context.getInstance();
        return (T)context.findByQualifier(qualifier);
    }
}
