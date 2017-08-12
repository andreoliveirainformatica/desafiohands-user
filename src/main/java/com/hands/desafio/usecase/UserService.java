package com.hands.desafio.usecase;

import com.hands.desafio.http.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse findById(String id);

    List<UserResponse> findAll(int page);

    void importUsers(final String[] users);
}
