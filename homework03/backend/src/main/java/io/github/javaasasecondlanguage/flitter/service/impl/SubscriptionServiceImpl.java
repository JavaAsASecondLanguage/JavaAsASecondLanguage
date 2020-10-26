package io.github.javaasasecondlanguage.flitter.service.impl;

import io.github.javaasasecondlanguage.flitter.model.Subscription;
import io.github.javaasasecondlanguage.flitter.model.User;
import io.github.javaasasecondlanguage.flitter.service.SubscriptionService;
import io.github.javaasasecondlanguage.flitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Qualifier("subscriptionRepository")
    Map<String, Subscription> repository;

    UserService userService;

    @Autowired
    public SubscriptionServiceImpl(Map<String, Subscription> repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public void clear() {
        repository.clear();
    }

    @Override
    public Subscription create(User authUser, Subscription subscription) {
        subscription.setId(UUID.randomUUID().toString());
        subscription.setSubscriberName(authUser.getUserName());
        repository.put(subscription.getId(), subscription);
        return subscription;
    }

    @Override
    public Subscription remove(User authUser, Subscription subscription) {
        var subscriptionToRemove = repository.values().stream()
                .filter(x ->
                        (authUser.getUserName().equals(x.getSubscriberName()) &&
                                x.getPublisherName().equals(subscription.getPublisherName()))
                ).findFirst().get();
        return repository.remove(subscriptionToRemove.getId());
    }

    @Override
    public List<String> findSubscriberNames(User authUser) {
        return repository.values().stream()
                .filter(x -> x.getPublisherName().equals(authUser.getUserName()))
                .map(x -> x.getSubscriberName())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findPublisherNames(User authUser) {
        return repository.values().stream()
                .filter(x -> x.getSubscriberName().equals(authUser.getUserName()))
                .map(x -> x.getPublisherName())
                .collect(Collectors.toList());
    }
}
