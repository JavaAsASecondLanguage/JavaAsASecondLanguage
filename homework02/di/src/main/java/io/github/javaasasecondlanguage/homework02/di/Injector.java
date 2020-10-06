package io.github.javaasasecondlanguage.homework02.di;

import io.github.javaasasecondlanguage.homework02.di.exceptions.NotFoundBeanFactoryException;
import io.github.javaasasecondlanguage.homework02.di.exceptions.UnresolvedDependenciesException;

import java.util.function.Supplier;


public class Injector {

    private static Context context;

    public static <T> T inject(Class<T> clazz) {
        Supplier<T> beanFactory = context.findBeanByClass(clazz);
        return checkAndReturnBeanThrowIfFailed(beanFactory);
    }

    public static <T> T inject(String qualifier) {
        Supplier<T> beanFactory = context.findBeanByClassAndQualifier(qualifier);
        return checkAndReturnBeanThrowIfFailed(beanFactory);
    }

    private static <T> T checkAndReturnBeanThrowIfFailed(Supplier<T> beanFactory) {
        if (beanFactory == null) {
            throw new NotFoundBeanFactoryException();
        }
        T bean = beanFactory.get();
        if (bean == null) {
            throw new UnresolvedDependenciesException();
        }
        return bean;
    }

    public static void registerContext(Context context) {
        Injector.setContext(context);
    }

    private static void setContext(Context otherContext) {
        context = otherContext;
    }
}
