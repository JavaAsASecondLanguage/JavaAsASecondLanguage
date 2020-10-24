package io.github.javaasasecondlanguage.flitter.model;

import io.github.javaasasecondlanguage.flitter.base.Flit;
import io.github.javaasasecondlanguage.flitter.base.FlitGet;
import io.github.javaasasecondlanguage.flitter.base.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Model class for flits processing
 */
public class FlitModel {

    private static final ConcurrentHashMap<User, ConcurrentLinkedQueue<Flit>> flits = new ConcurrentHashMap<>();
    private static final ConcurrentLinkedQueue<Flit> flitsList = new ConcurrentLinkedQueue<>();

    public static void addFlit(String token, String flit) {
        Flit f = new Flit(UserModel.getUserByToken(token), flit);
        if (!flits.containsKey(f.getUser())) {
            flits.put(f.getUser(), new ConcurrentLinkedQueue<>());
        }
        flits.get(f.getUser()).add(f);
        flitsList.add(f);
    }

    public static List<FlitGet> discover() {
        List<FlitGet> result = new ArrayList<>();
        var it = flitsList.iterator();
        int count = 0;
        while (it.hasNext() && count < 10) {
            result.add(it.next().get());
            count += 1;
        }
        return result;
    }

    public static List<FlitGet> getFlits(User user) {
        final List<FlitGet> result = new ArrayList<>();
        if (flits.containsKey(user)) {
            flits.get(user).forEach( f -> result.add(f.get()));
        }
        return result;
    }

    public static List<FlitGet> getFeed(User user) {
        final List<FlitGet> result = new ArrayList<>();
        if (SubscribeModel.isSubscriber(user)) {
            SubscribeModel.getPublishers(user).forEach(u -> {
                result.addAll(getFlits(UserModel.getUserByName(u)));
            });
        }
        return result;
    }

    public static void clear(){
        flits.clear();
        flitsList.clear();
    }
}
