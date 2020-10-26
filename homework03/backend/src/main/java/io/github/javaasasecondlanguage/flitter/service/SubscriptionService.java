package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.model.Subscription;
import io.github.javaasasecondlanguage.flitter.model.User;

import java.util.List;

public interface SubscriptionService {

    void clear();

    Subscription create(User authUser, Subscription subscription);

    Subscription remove(User authUser, Subscription subscription);

    List<String> findSubscriberNames(User authUser);

    List<String> findPublisherNames(User authUser);
}
