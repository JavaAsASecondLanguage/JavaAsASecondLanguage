package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.dao.SubscriptionRepository;
import io.github.javaasasecondlanguage.flitter.dto.SubscriptionDto;
import io.github.javaasasecondlanguage.flitter.dto.UserDto;
import io.github.javaasasecondlanguage.flitter.entity.Subscription;
import io.github.javaasasecondlanguage.flitter.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;

  public SubscriptionService(SubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  @Transactional
  public Subscription subscribe(UserDto subscriber, UserDto publisher) {
    var author = new User().setUserName(publisher.getUserName());
    var user = new User().setUserName(subscriber.getUserName());
    var subscription = subscriptionRepository.findByPublisherAndSubscriber(author, user)
        .orElseGet(() -> new Subscription().setPublisher(author))
        .setSubscriber(user);
    subscription = subscriptionRepository.save(subscription);
    return subscription;
  }

  public boolean unsubscribe(UserDto subscriber, UserDto publisher) {
    var author = new User().setUserName(publisher.getUserName());
    User user = new User().setUserName(subscriber.getUserName());
    return subscriptionRepository.findByPublisherAndSubscriber(author, user)
        .map(sub -> {
          subscriptionRepository.delete(sub);
          return true;
        }).orElse(false);
  }

  public List<SubscriptionDto> publishers(UserDto subscriber) {
    return subscriptionRepository.findBySubscriber(new User().setUserName(subscriber.getUserName()))
        .stream()
        .map(sub -> new SubscriptionDto(sub.getId().toString(), sub.getPublisher().getUserName(), sub.getSubscriber().getUserName()))
        .collect(Collectors.toList());
  }

  public List<SubscriptionDto> subscribers(UserDto publisher) {
    return subscriptionRepository.findByPublisher(new User().setUserName(publisher.getUserName()))
        .stream()
        .map(sub -> new SubscriptionDto(sub.getId().toString(), sub.getPublisher().getUserName(), sub.getSubscriber().getUserName()))
        .collect(Collectors.toList());
  }
}
