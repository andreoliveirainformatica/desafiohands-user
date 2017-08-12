package com.hands.desafio.http.dto;

import com.hands.desafio.entity.User;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class UserResponse implements Serializable {

    @Id
    private String id;

    private Set<String> devices;

    public static UserResponse builder(final User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }
    
}
