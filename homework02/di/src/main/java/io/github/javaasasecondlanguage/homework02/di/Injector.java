package io.github.javaasasecondlanguage.homework02.di;

public class Injector {
    private static Context context = new Context();

    public static <T> T inject(Class<T> clazz) {
        return (T)context.findByClass(clazz);
    }

    public static <T> T inject(Class<T> clazz, String qualifier) {
        return (T) context.findByQualifier(qualifier);
    }
}
