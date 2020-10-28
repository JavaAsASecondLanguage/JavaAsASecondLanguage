package io.github.javaasasecondlanguage.flitter.dao;

import io.github.javaasasecondlanguage.flitter.entity.Subscription;
import io.github.javaasasecondlanguage.flitter.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, UUID> {
  List<Subscription> findByPublisher(User publisher);

  Optional<Subscription> findByPublisherAndSubscriber(User publisher, User subscriber);

  List<Subscription> findBySubscriber(User subscriber);
}
