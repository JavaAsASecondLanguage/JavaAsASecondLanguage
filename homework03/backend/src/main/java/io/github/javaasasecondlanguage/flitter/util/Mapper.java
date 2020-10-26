package io.github.javaasasecondlanguage.flitter.util;

import io.github.javaasasecondlanguage.flitter.dto.AddFlitDto;
import io.github.javaasasecondlanguage.flitter.dto.SubscriptionDto;
import io.github.javaasasecondlanguage.flitter.model.Flit;
import io.github.javaasasecondlanguage.flitter.model.Subscription;
import io.github.javaasasecondlanguage.flitter.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static Flit toFlit(AddFlitDto addFlitDto) {
        var flit = new Flit();
        flit.setContent(addFlitDto.getContent());
        return flit;
    }

    public static List<String> toUserNames(List<User> users) {
        return users.stream().map(x -> x.getUserName()).collect(Collectors.toList());
    }

    public static Subscription toSubscription(SubscriptionDto subscriptionDto) {
        var subscription = new Subscription();
        subscription.setPublisherName(subscriptionDto.getPublisherName());
        return subscription;
    }
}
