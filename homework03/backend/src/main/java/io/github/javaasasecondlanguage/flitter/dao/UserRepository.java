package io.github.javaasasecondlanguage.flitter.dao;

import io.github.javaasasecondlanguage.flitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUserToken(UUID userToken);

  Optional<User> findByUserName(String userName);
}
