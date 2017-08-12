package com.hands.desafio.gateway;

import com.hands.desafio.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserGateway {

    List<User> findAll(int page);

    Optional<User> findOne(String id);

    void save(User users);
}
