package io.github.javaasasecondlanguage.flitter.service.impl;

import io.github.javaasasecondlanguage.flitter.model.Flit;
import io.github.javaasasecondlanguage.flitter.model.User;
import io.github.javaasasecondlanguage.flitter.service.FlitService;
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
public class FlitServiceImpl implements FlitService {

    @Qualifier("flitRepository")
    private final Map<String, Flit> repository;

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public FlitServiceImpl(Map<String, Flit> repository, UserService userService, SubscriptionService subscriptionService) {
        this.repository = repository;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void clear() {
        repository.clear();
    }

    @Override
    public Flit create(User authUser, Flit flit) {
        flit.setId(UUID.randomUUID().toString());
        flit.setUserName(authUser.getUserName());
        repository.put(flit.getId(), flit);
        return flit;
    }

    @Override
    public List<Flit> getLastFlits(int i) {
        return repository.values().stream()
                .skip(Math.max(0, repository.size() - i))
                .limit(i)
                .collect(Collectors.toList());
    }

    @Override
    public List<Flit> getFlitsByPublisherName(String username) {
        var user = userService.getUserByName(username);
        return repository.values().stream()
                .filter(x -> x.getUserName().equals(user.getUserName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flit> getFlitsBySubscriber(User authUser) {
        var publisherNames = subscriptionService.findPublisherNames(authUser);
        return repository.values().stream()
                .filter(x -> publisherNames.contains(x.getUserName()))
                .collect(Collectors.toList());
    }
}
