package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.model.Subscription;
import io.github.javaasasecondlanguage.flitter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class SubscriptionServiceImpl implements SubscriptionService{

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
    public Subscription create(Subscription subscription) {
        var authUser = userService.getUserByToken(subscription.getSubscriberToken());
        subscription.setId(UUID.randomUUID().toString());
        subscription.setSubscriberName(authUser.getUserName());
        return repository.put(subscription.getId(), subscription);
    }

    @Override
    public Subscription remove(Subscription subscription) {
        var authUser = userService.getUserByToken(subscription.getSubscriberToken());
        var subscriptionToRemove = repository.values().stream()
                .filter(x ->
                        (authUser.getUserName().equals(x.getSubscriberName()) &&
                                x.getPublisherName().equals(subscription.getPublisherName()))
                ).findFirst().get();
        return repository.remove(subscriptionToRemove.getId());
    }

    @Override
    public List<User> findSubscribers(String userToken) {
        return userService.getListByNames(
                repository.values().stream()
                        .map(x -> x.getPublisherName())
                        .filter(x -> equals(userToken))
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public List<User> findPublishers(String userToken) {
        return userService.getListByNames(
                repository.values().stream()
                        .map(x -> x.getSubscriberName())
                        .filter(x -> equals(userToken))
                        .collect(Collectors.toSet())
        );
    }
}
