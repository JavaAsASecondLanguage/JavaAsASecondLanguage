package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.model.Flit;

import java.util.List;

public interface FlitService {

    void clear();

    Flit create(Flit flit);

    List<Flit> getLastFlits(int i);

    List<Flit> getFlitsByUserName(String username);

    List<Flit> getFlitsByUserToken(String userToken);
}
