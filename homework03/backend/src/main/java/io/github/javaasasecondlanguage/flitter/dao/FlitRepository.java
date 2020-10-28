package io.github.javaasasecondlanguage.flitter.dao;

import io.github.javaasasecondlanguage.flitter.entity.Flit;
import io.github.javaasasecondlanguage.flitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

// hate spring jpa, but i'm lazy
public interface FlitRepository extends JpaRepository<Flit, UUID> {
  List<Flit> findAllByAuthor_UserNameInOrderByCreationTime(Collection<String> collect);

  List<Flit> findAllByAuthor(User user);
}
