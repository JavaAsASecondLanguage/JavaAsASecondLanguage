package io.github.javaasasecondlanguage.homework02.di;

public class Injector {
    private static Context resolutionScope = Context.getRoot();

    public static void setResolutionScope(String name) {
        resolutionScope = Context.getResolutionScope(name);
    }

    public static <T> T inject(Class<T> clazz) {
        return resolutionScope.resolve(clazz);
    }

    public static <T> T inject(Class<T> clazz, String qualifier) {
        return resolutionScope.resolve(clazz, qualifier);
    }
}
