package io.github.javaasasecondlanguage.homework02.di;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Injector {
    static final String DEFAULT_QUALIFIER = "";
    private static Context context;

    public static void init(Context ctx) {
        context = ctx;
    }

    public static <T> T inject(Class<T> clazz) {
        return inject(clazz, DEFAULT_QUALIFIER);
    }

    @SuppressWarnings("unchecked")
    public static <T> T inject(Class<T> clazz, String qualifier) {
        if (clazz.isInterface()) {
            return (T) Proxy.newProxyInstance(
                    clazz.getClassLoader(),
                    new Class<?>[]{clazz},
                    new InvocationHandler() {
                        Object wrapped = null;

                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args)
                                throws Throwable {
                            if (wrapped == null) {
                                wrapped = context.find(clazz, qualifier);
                            }
                            return method.invoke(wrapped, args);
                        }
                    }
            );
        }
        return (T) context.find(clazz, qualifier);
    }
}

