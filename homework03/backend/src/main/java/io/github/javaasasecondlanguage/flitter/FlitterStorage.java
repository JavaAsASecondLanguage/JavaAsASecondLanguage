package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.models.Flit;
import io.github.javaasasecondlanguage.flitter.models.User;

import java.util.*;
import java.util.stream.Collectors;

public class FlitterStorage {
    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, User> usersByToken = new HashMap<>();

    private ArrayList<Flit> flits = new ArrayList();

    public void clear() {
        users.clear();
        usersByToken.clear();
        flits.clear();
    }

    public Collection<User> listUsers() {
        return users.values();
    }

    public User registerUser(String name) {
        if (!users.containsKey(name)) {
            var user = new User(name);
            users.put(name, user);
            usersByToken.put(user.token, user);
            return user;
        } else {
            return null;
        }
    }

    public User authenticate(String token) {
        if (usersByToken.containsKey(token)) {
            return usersByToken.get(token);
        }

        return null;
    }

    public User getUser(String userName) {
        if (users.containsKey(userName)) {
            return users.get(userName);
        }

        return null;
    }

    public Flit addFlit(User user, String content) {
        var flit = new Flit(user, content);
        flits.add(flit);
        return flit;
    }

    public List<Flit> getLatestFlits(int topN) {
        var toSkip = Math.max(flits.size() - topN, 0);

        return flits.stream()
                .skip(toSkip)
                .collect(Collectors.toList());
    }

    public List<Flit> getUserFlits(User user) {
        return flits.stream()
                .filter(f -> f.user.equals(user))
                .collect(Collectors.toList());
    }

    public List<Flit> getUserFeedFlits(User user) {
        return flits.stream()
                .filter(f -> user.subscribedTo.contains(f.user))
                .collect(Collectors.toList());
    }

    public void subscribe(User subscriber, User publisher) {
        subscriber.subscribe(publisher);
    }

    public void unsubscribe(User subscriber, User publisher) {
        subscriber.unsubscribe(publisher);
    }

    public List<User> getSubscribers(User user) {
        return users.values().stream()
                .filter(u -> u.subscribed(user))
                .collect(Collectors.toList());
    }

    public List<User> getPublishers(User user) {
        return user.subscribedTo.stream()
                .map(u -> (User)u)
                .collect(Collectors.toList());
    }
}
