package io.github.javaasasecondlanguage.homework02.di;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Контекст для регистрации внедряемых зависимостей.
 */
public class Context {
    private static final Map<String, List<Object>> dependencies = new HashMap<>();

    /**
     * Зарегистрировать зависимость с указанием квалификатора.
     *
     * @param object    регистрируемая зависимость
     * @param qualifier квалификатор зависимости
     * @return контекст
     */
    public <T> Context register(T object, String qualifier) {
        dependencies.compute(qualifier, (q, qmap) -> {
            if (qmap == null) {
                qmap = new ArrayList<>();
            }
            qmap.add(object);
            return qmap;
        });
        return this;
    }

    /**
     * Зарегистрировать зависимость
     *
     * @param object зависимость
     * @return контекст
     */
    public <T> Context register(T object) {
        return register(object, null);
    }

    /**
     * Получить все зарегистрированные зависимости.
     */
    protected static Map<String, List<Object>> getDependencies() {
        return dependencies;
    }

}
