package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void clear();

    User create(User user);

    List<User> getAll();

    User getUserByToken(String userToken);

    User getUserByName(String username);

    List<User> getListByNames(Set<String> tokens);

}
