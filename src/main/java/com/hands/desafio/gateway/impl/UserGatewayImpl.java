package com.hands.desafio.gateway.impl;

import com.hands.desafio.entity.User;
import com.hands.desafio.gateway.UserGateway;
import com.hands.desafio.gateway.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Profile("!test")
public class UserGatewayImpl implements UserGateway {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll(int page) {
        return userRepository.findAll(new PageRequest(page, 20)).getContent();
    }
    public Optional<User> findOne(String id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }


    public void save(User user) {
        userRepository.save(user);
    }
}
