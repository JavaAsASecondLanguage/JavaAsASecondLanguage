package io.github.javaasasecondlanguage.homework02.di;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Injector {

    public static <T> T inject(Class<T> clazz) {
        return inject(clazz, "");
    }

    public static <T> T inject(Class<T> clazz, String qualifier) {
        T object = Context.getContext(clazz, qualifier);
        if (!clazz.isInterface()) {
            return object;
        } else {
            var interfaceProxy = (T) Proxy.newProxyInstance(
                    clazz.getClassLoader(),
                    new Class<?>[]{clazz},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args)
                                throws Throwable {
                            return method.invoke(object, args);
                        }
                    });
            return interfaceProxy;
        }

    }

}
