package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.model.Subscription;
import io.github.javaasasecondlanguage.flitter.model.User;

import java.util.List;

public interface SubscriptionService {

    void clear();

    Subscription create(Subscription subscription);

    Subscription remove(Subscription subscription);

    List<User> findSubscribers(String userToken);

    List<User> findPublishers(String userToken);
}
