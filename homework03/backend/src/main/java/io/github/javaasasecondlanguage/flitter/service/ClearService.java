package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.dao.FlitRepository;
import io.github.javaasasecondlanguage.flitter.dao.SubscriptionRepository;
import io.github.javaasasecondlanguage.flitter.dao.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ClearService {
  private final UserRepository userRepository;
  private final SubscriptionRepository subscriptionRepository;
  private final FlitRepository flitRepository;

  public ClearService(UserRepository userRepository, SubscriptionRepository subscriptionRepository, FlitRepository flitRepository) {
    this.userRepository = userRepository;
    this.subscriptionRepository = subscriptionRepository;
    this.flitRepository = flitRepository;
  }

  // drop'em all
  @Transactional
  public void clear() {
    flitRepository.deleteAll();
    subscriptionRepository.deleteAll();
    userRepository.deleteAll();
  }
}
