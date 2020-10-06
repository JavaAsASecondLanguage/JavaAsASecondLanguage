package io.github.javaasasecondlanguage.homework02.di;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

public class ProxyHelper {
    static <T> T createProxy(Class<T> clazz, Supplier<T> originAccessor) {
        if (clazz.isInterface()) {
            return createInterfaceProxy(clazz, originAccessor);
        } else {
            return createCglibProxy(clazz, originAccessor);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T createCglibProxy(Class<T> clazz, Supplier<T> originAccessor) {
        return (T) Enhancer.create(clazz, new ProxyCglibHandler<>(originAccessor));
    }

    @SuppressWarnings("unchecked")
    private static <T> T createInterfaceProxy(Class<T> clazz, Supplier<T> originAccessor) {
        return (T) Proxy.newProxyInstance(
                ProxyHelper.class.getClassLoader(),
                new Class[]{clazz},
                new ProxyHandler<>(originAccessor)
        );
    }

    private static class ProxyCglibHandler<T> implements MethodInterceptor {
        private T origin = null;
        private final Supplier<T> originAccessor;

        public ProxyCglibHandler(Supplier<T> originAccessor) {
            this.originAccessor = originAccessor;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
                throws Throwable {
            if (origin == null) {
                origin = originAccessor.get();
            }
            return proxy.invoke(origin, args);
        }
    }

    private static class ProxyHandler<T> implements InvocationHandler {
        private T origin = null;
        private final Supplier<T> originAccessor;

        public ProxyHandler(Supplier<T> originAccessor) {
            this.originAccessor = originAccessor;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (origin == null) {
                origin = originAccessor.get();
            }
            return method.invoke(origin, args);
        }
    }
}
