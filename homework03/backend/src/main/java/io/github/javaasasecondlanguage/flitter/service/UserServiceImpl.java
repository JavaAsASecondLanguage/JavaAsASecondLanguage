package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.github.javaasasecondlanguage.flitter.util.ValidationUtil.checkExists;
import static io.github.javaasasecondlanguage.flitter.util.ValidationUtil.checkNotFound;

@Service
public class UserServiceImpl implements UserService {

    @Qualifier("userRepository")
    private final Map<String, User> repository;

    public UserServiceImpl(Map<String, User> repository) {
        this.repository = repository;
    }

    @Override
    public void clear() {
        repository.clear();
    }

    @Override
    public User create(User user) {
        user.setUserToken(UUID.randomUUID().toString());
        checkExists(repository.get(user.getUserName()), "This name");
        repository.put(user.getUserName(), user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return repository.values().stream()
                .collect(Collectors.toList());
    }

    //todo: исправить
    @Override
    public User getUserByToken(String userToken) {
        return checkNotFound(repository.values().stream()
                .filter(x -> x.getUserToken().equals(userToken))
                .findFirst().get(), "User");
    }

    @Override
    public User getUserByName(String username) {
        return checkNotFound(repository.get(username), "User");
    }

    @Override
    public List<User> getListByNames(Set<String> names) {
        return repository.values().stream()
                .filter(x -> names.contains(x))
                .collect(Collectors.toList());
    }
}
