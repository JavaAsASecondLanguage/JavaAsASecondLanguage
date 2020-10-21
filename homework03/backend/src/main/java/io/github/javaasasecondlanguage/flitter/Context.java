package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.pojos.Flit;

import java.util.*;

/**
 * Контекст работы приложения.
 */
public class Context {
    //-------------------------------------------
    // static section
    private static Context instance;

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    //-------------------------------------------
    // NON static section
    private final Map<UUID, String> users;
    private final List<Flit> flits;
    private final Map<UUID, List<UUID>> subscriptions;

    private Context() {
        users = new HashMap<>();
        flits = new ArrayList<>();
        subscriptions = new HashMap<>();
    }

    public void clear() {
        users.clear();
        flits.clear();
        subscriptions.clear();
    }

    /**
     * Карта зарегистрированных пользователей. Ключ - идентификатор, значение - наименование.
     */
    protected Map<UUID, String> getUserTokens() {
        return users;
    }

    /**
     * Получить все сообщения.
     */
    public List<Flit> getFlits() {
        return flits;
    }

    /**
     * Получить все подписки. Ключ - идентификатор подписчика.
     * Значение - список идентификаторов пользователей, на которые произведена подписка.
     */
    public Map<UUID, List<UUID>> getSubscriptions() {
        return subscriptions;
    }
}
