package com.hands.desafio.usecase.convert.csv;

import com.hands.desafio.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class UserConverter implements Converter<String, User> {
    @Override
    public User convert(String line) {
        final String[] p = line.split(";");
        User user = new User();
        user.setId(p[0]);
        user.setName("user - ".concat(p[0].trim()));
        user.setDevices(new HashSet<>(Arrays.asList(p[1].trim())));
        return user;
    }
}
