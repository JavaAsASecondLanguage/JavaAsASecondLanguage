package io.github.javaasasecondlanguage.flitter.model;

import io.github.javaasasecondlanguage.flitter.base.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * Model class for subscribing
 */
public class SubscribeModel {

    private static final ConcurrentHashMap<User, ConcurrentSkipListSet<User>> subscriber = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<User, ConcurrentSkipListSet<User>> publisher = new ConcurrentHashMap<>();

    public static void subscribe(User subscriber, User publisher) {
        if (!SubscribeModel.subscriber.containsKey(publisher)) {
            SubscribeModel.subscriber.put(publisher, new ConcurrentSkipListSet<>());
        }
        if (!SubscribeModel.publisher.containsKey(subscriber)) {
            SubscribeModel.publisher.put(subscriber, new ConcurrentSkipListSet<>());
        }
        SubscribeModel.subscriber.get(publisher).add(subscriber);
        SubscribeModel.publisher.get(subscriber).add(publisher);
    }

    public static void unSubscribe(User subscriber, User publisher) {
        if (SubscribeModel.subscriber.containsKey(publisher)) {
            SubscribeModel.subscriber.get(publisher).remove(subscriber);
        }
        if (SubscribeModel.publisher.containsKey(subscriber)) {
            SubscribeModel.publisher.get(subscriber).remove(publisher);
        }
    }

    public static boolean isSubscriber(User subscriber) {
        return SubscribeModel.publisher.containsKey(subscriber);
    }

    public static List<String> getSubscribers(User user) {
        if (subscriber.containsKey(user)) {
            return subscriber.get(user).stream().map( u -> u.getUserName()).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static List<String> getPublishers(User user) {
        if (publisher.containsKey(user)) {
            return publisher.get(user).stream().map( u -> u.getUserName()).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static void clear(){
        subscriber.clear();
        publisher.clear();
    }

}
