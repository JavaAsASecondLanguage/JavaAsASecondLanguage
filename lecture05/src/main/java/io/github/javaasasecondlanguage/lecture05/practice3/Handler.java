package io.github.javaasasecondlanguage.lecture05.practice3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Handler implements InvocationHandler {
    private final Object original;
    private ArrayList<List<?>> snapshots = new ArrayList<>();

    public Handler(List<?> original) {
        this.original = original;
    }

    public Handler(Map<?, ?> original) {
        this.original = original;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        var res = method.invoke(original, args);

        if (List.class.isAssignableFrom(original.getClass())) {
            var list = (List<?>) original;
            System.out.println("Size: " + list.size());
            snapshots.add(
                list.stream().collect(Collectors.toList())
            );
            System.out.println(snapshots);
        } else if (Map.class.isAssignableFrom(original.getClass())) {
            var map = (Map<?, ?>) original;
            if (method.getName() == "put" || method.getName() == "remove") {
                System.out.println("Size: " + map.size());
                snapshots.add(
                        map.entrySet()
                                .stream()
                                .collect(Collectors.toList())
                );
                System.out.println(snapshots);
            }
        }

        return res;
    }
}