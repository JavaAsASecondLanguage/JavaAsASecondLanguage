package io.github.javaasasecondlanguage.homework02.di;

public class Injector {

    private static Context context;

    public static <T> T inject(Class<T> clazz) {
        return context.getBean(clazz, null);
    }

    public static <T> T inject(Class<T> clazz, String qualifier) {
        return context.getBean(clazz, qualifier);
    }

    static void setContext(Context context) {
        Injector.context = context;
    }
}
