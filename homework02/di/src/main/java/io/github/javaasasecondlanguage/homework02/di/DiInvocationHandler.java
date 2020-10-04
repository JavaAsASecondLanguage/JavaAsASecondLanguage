package io.github.javaasasecondlanguage.homework02.di;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Обработчик вызова методов проксированных зависимостей.
 * При каждом вызове метода проксированной зависимости,
 * производится поиск зависимости в контексте {@link Context}.
 * <p>
 * Если зависимость была найдена, соответствующий метод вызывается у нее.
 * <p>
 * При отсутствии зарегистрированной зависимости кидается исключение {@link NullPointerException}
 */
class DiInvocationHandler implements InvocationHandler {
    private final Class<?> clazz;
    private final String qualifier;

    DiInvocationHandler(Class<?> clazz, String qualifier) {
        this.clazz = clazz;
        this.qualifier = qualifier;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object dependency = Injector.getDependency(clazz, qualifier);
        if (dependency == null) {
            throw new NullPointerException();
        }
        return method.invoke(dependency, args);
    }
}
