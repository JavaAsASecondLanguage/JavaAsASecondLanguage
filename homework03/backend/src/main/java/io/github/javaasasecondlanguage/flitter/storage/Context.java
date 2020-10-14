package io.github.javaasasecondlanguage.flitter.storage;

import io.github.javaasasecondlanguage.flitter.model.FlitModel;
import io.github.javaasasecondlanguage.flitter.model.SubscriptionModel;
import io.github.javaasasecondlanguage.flitter.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Context {
    Map<String, String> userToToken = new ConcurrentHashMap<>();
    Map<String, String> tokenToUser = new ConcurrentHashMap<>();
    Map<String, List<FlitModel>> flits = new ConcurrentHashMap<>();
    Map<String, Set<String>> publisherToSubscribers = new ConcurrentHashMap<>();
    Map<String, Set<String>> subscriberToPublishers = new ConcurrentHashMap<>();
    private static volatile Context instance;

    private Context() {
    }

    public static Context getInstance() {
        if (instance == null) {
            recreateInstance();
        }
        return instance;
    }

    public static void recreateInstance() {
        synchronized (Context.class) {
            instance = new Context();
        }
    }

    public Optional<Map<String, String>> registerUser(String userName) {
        if (!userToToken.containsKey(userName)) {
            String token = UUID.randomUUID().toString();
            synchronized (this) {
                userToToken.put(userName, token);
                tokenToUser.put(token, userName);
            }
            return Optional.of(Map.of(
                    "userName", userName,
                    "userToken", token
            ));
        }
        return Optional.empty();
    }

    public List<String> getUsers() {
        return new ArrayList<>(userToToken.keySet());
    }

    public Optional<String> addFlit(FlitModel flit) {
        if (flit == null) {
            return Optional.of("Flit is null");
        } else if (!checkUserToken(flit.userToken())) {
            return Optional.of("Unauthorized access");
        } else if (!checkContent(flit.content())) {
            return Optional.of("Empty content");
        }

        var userName = tokenToUser.get(flit.userToken());
        flits.putIfAbsent(userName, new ArrayList<>());
        var timestamp = System.currentTimeMillis();
        flits.get(userName).add(
                new FlitModel(userName, flit.userToken(), flit.content(), timestamp)
        );
        return Optional.empty();
    }

    private boolean checkUserToken(String token) {
        return token != null && tokenToUser.containsKey(token);
    }

    private boolean checkContent(String content) {
        return content != null && content.length() > 0;
    }

    public List<Map<String, String>> discoverFlits() {
        return flits.values().stream().flatMap(List::stream)
                .sorted(Comparator.comparingLong(FlitModel::timestamp).reversed())
                .limit(10).map(Context::flitSerialize).collect(Collectors.toList());
    }

    public List<Map<String, String>> flitsOf(String userName) {
        if (userName == null || !userToToken.containsKey(userName)) {
            return null;
        }
        return flits.getOrDefault(userName, new ArrayList<>()).stream()
                .map(Context::flitSerialize).collect(Collectors.toList());
    }

    public List<Map<String, String>> getFeed(String userToken) {
        if (!checkUserToken(userToken)) {
            return null;
        }
        var userName = tokenToUser.get(userToken);
        return subscriberToPublishers.getOrDefault(userName, new HashSet<>()).stream()
                .map(name -> flits.getOrDefault(name, new ArrayList<>())).flatMap(List::stream)
                .map(Context::flitSerialize).collect(Collectors.toList());
    }

    private static Map<String, String> flitSerialize(FlitModel fl) {
        return Map.of(
                "userName", fl.userName(),
                "content", fl.content()
        );
    }

    public Optional<String> subscribeTo(SubscriptionModel subscription) {
        var reason = checkSubscription(subscription);
        if (reason.isEmpty()) {
            String subscriberName = tokenToUser.get(subscription.subscriberToken());
            synchronized (this) {
                subscriberToPublishers.putIfAbsent(subscriberName, new HashSet<>());
                subscriberToPublishers.get(subscriberName).add(subscription.publisherName());
                publisherToSubscribers.putIfAbsent(subscription.publisherName(), new HashSet<>());
                publisherToSubscribers.get(subscription.publisherName()).add(subscriberName);
            }
        }
        return reason;
    }

    public Optional<String> unsubscribeTo(SubscriptionModel subscription) {
        var reason = checkSubscription(subscription);
        if (reason.isEmpty()) {
            String subscriberName = tokenToUser.get(subscription.subscriberToken());
            synchronized (this) {
                subscriberToPublishers.putIfAbsent(subscriberName, new HashSet<>());
                subscriberToPublishers.get(subscriberName).remove(subscription.publisherName());
                publisherToSubscribers.putIfAbsent(subscription.publisherName(), new HashSet<>());
                publisherToSubscribers.get(subscription.publisherName()).remove(subscriberName);
            }
        }
        return reason;
    }

    private Optional<String> checkSubscription(SubscriptionModel subscription) {
        if (!userToToken.containsKey(subscription.publisherName())) {
            return Optional.of("Publisher is unknown");
        } else if (!tokenToUser.containsKey(subscription.subscriberToken())) {
            return Optional.of("Subscriber is unknown");
        }
        return Optional.empty();
    }

    public List<String> getSubscribers(String userToken) {
        if (!checkUserToken(userToken)) {
            return null;
        }
        String userName = tokenToUser.get(userToken);
        return new ArrayList<>(publisherToSubscribers.getOrDefault(userName, new HashSet<>()));
    }

    public List<String> getPublishers(String userToken) {
        if (!checkUserToken(userToken)) {
            return null;
        }
        String userName = tokenToUser.get(userToken);
        return new ArrayList<>(subscriberToPublishers.getOrDefault(userName, new HashSet<>()));
    }
}
