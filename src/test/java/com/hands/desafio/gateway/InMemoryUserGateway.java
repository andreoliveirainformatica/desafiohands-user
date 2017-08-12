package com.hands.desafio.gateway;

import com.hands.desafio.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryUserGateway implements UserGateway {

    private Map<String, User> cache = new HashMap<>();

    @Override
    public List<User> findAll(int page) {
        return cache.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<User> findOne(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public void save(User user) {
        this.cache.put(user.getId(), user);
    }

    public void clearCache() {
        this.cache.clear();
    }
}
