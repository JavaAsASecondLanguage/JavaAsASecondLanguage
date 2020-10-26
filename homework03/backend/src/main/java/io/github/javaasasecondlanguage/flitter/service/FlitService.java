package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.dto.AddFlitDto;
import io.github.javaasasecondlanguage.flitter.model.Flit;
import io.github.javaasasecondlanguage.flitter.model.User;

import java.util.List;

public interface FlitService {

    void clear();

    Flit create(User authUser, Flit flit);

    List<Flit> getLastFlits(int i);

    List<Flit> getFlitsByPublisherName(String username);

    List<Flit> getFlitsBySubscriber(User authUser);
}
