package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.model.Flit;
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

    @Autowired
    public FlitServiceImpl(Map<String, Flit> repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public void clear() {
        repository.clear();
    }

    @Override
    public Flit create(Flit flit) {
        var authUser = userService.getUserByToken(flit.getUserToken());
        flit.setId(UUID.randomUUID().toString());
        flit.setUserName(authUser.getUserToken());
        return repository.put(flit.getId(), flit);
    }

    @Override
    public List<Flit> getLastFlits(int i) {
        return repository.values().stream()
                .limit(i)
                .collect(Collectors.toList());
    }

    @Override
    public List<Flit> getFlitsByUserName(String username) {
        var user = userService.getUserByName(username);
        return repository.values().stream()
                .filter(x -> x.getUserName().equals(user.getUserToken()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flit> getFlitsByUserToken(String userToken) {
        var authUser = userService.getUserByToken(userToken);
        return repository.values().stream()
                .filter(x -> authUser.getUserToken().equals(userToken))
                .collect(Collectors.toList());
    }
}
