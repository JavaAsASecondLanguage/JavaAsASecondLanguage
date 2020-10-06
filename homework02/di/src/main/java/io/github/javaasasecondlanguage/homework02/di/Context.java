package io.github.javaasasecondlanguage.homework02.di;


import io.github.javaasasecondlanguage.homework02.di.exceptions.AmbigiousBeanQueryException;
import io.github.javaasasecondlanguage.homework02.di.exceptions.BeanAlreadyDefinedException;
import io.github.javaasasecondlanguage.homework02.di.exceptions.BeanNotFoundException;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.javaasasecondlanguage.homework02.di.ContextRegistry.DEFAULT_CONTEXT_NAME;

public class Context {

    static final String DEFAULT_QUALIFIER = "DEFAULT_QUALIFIER";
    private final String name;

    private final Set<Bean<?>> beans = new HashSet<>();
    private final Map<Class<?>, Map<String, Object>> proxies = new HashMap<>();

    public Context() {
        this(DEFAULT_CONTEXT_NAME);
    }

    public Context(String contextName) {
        this.name = contextName;

        ContextRegistry.registerContext(this);
        register(this, contextName);
    }


    public <T> Context register(T object, String qualifier) {
        var bean = new Bean<>(object, qualifier);
        if (beans.contains(bean)) {
            throw new BeanAlreadyDefinedException(object.getClass(), qualifier);
        }
        beans.add(bean);
        return this;
    }

    public <T> Context register(T object) {
        return register(object, DEFAULT_QUALIFIER);
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    <T> T getRawBean(Class<T> clazz, String qualifier) {
        var beans = this.beans.stream()
                .filter(bean -> bean.isApplicable(clazz))
                .filter(bean -> qualifier.equals(DEFAULT_QUALIFIER)
                        || qualifier.equals(bean.getQualName()))
                .collect(Collectors.toList());
        if (beans.isEmpty()) {
            throw new BeanNotFoundException(clazz, qualifier);
        } else if (beans.size() > 1) {
            if (qualifier.equals(DEFAULT_QUALIFIER)) {
                beans = beans.stream()
                        .filter(bean -> qualifier.equals(bean.getQualName()))
                        .collect(Collectors.toList());
            }
            if (beans.size() == 1) {
                return (T) beans.get(0).getObject();
            }
            throw new AmbigiousBeanQueryException(clazz, qualifier);
        } else {
            return (T) beans.get(0).getObject();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz, String qualName) {
        if (Modifier.isFinal(clazz.getModifiers())) {
            return getRawBean(clazz, qualName);
        }
        var classProxies = proxies.computeIfAbsent(clazz, (cls) -> new HashMap<>());
        return (T) classProxies.computeIfAbsent(qualName, (qual) -> ProxyHelper.createProxy(
                clazz, () -> getRawBean(clazz, qual)));
    }

    public <T> T getBean(Class<T> clazz) {
        return getBean(clazz, DEFAULT_QUALIFIER);
    }
}
