package com.hands.desafio.usecase.impl;

import com.hands.desafio.entity.User;
import com.hands.desafio.exception.NotFoundException;
import com.hands.desafio.gateway.UserGateway;
import com.hands.desafio.http.dto.UserResponse;
import com.hands.desafio.usecase.UserService;
import com.hands.desafio.usecase.convert.csv.UserConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by andre.oliveira on 2/23/17.
 */
@Component
@AllArgsConstructor()
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserGateway userGateway;

    private final UserConverter userConverter;

    @Override
    public UserResponse findById(String id) {
        return userGateway
                .findOne(id)
                .map(user -> UserResponse.builder(user)).orElseThrow(() -> new NotFoundException());
    }

    @Override
    public List<UserResponse> findAll(int page) {
        return userGateway
                .findAll(page)
                .stream()
                .map(user -> UserResponse.builder(user))
                .collect(Collectors.toList());
    }

    @Override
    public void importUsers(String[] users) {

        final Map<String, Optional<User>> matrixUsers =
                Arrays
                        .stream(users)
                        .map(line -> userConverter.convert(line))
                        .collect(Collectors.groupingBy(User::getId,
                                Collectors.reducing((user, user2) -> {
                                    user.getDevices().addAll(user2.getDevices());
                                    return user;
                                })));
        matrixUsers.values().forEach(optUser -> optUser.ifPresent(user -> userGateway.save(user)));
    }

}
