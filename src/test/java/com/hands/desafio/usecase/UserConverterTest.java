package com.hands.desafio.usecase;

import com.hands.desafio.entity.User;
import com.hands.desafio.http.dto.UserResponse;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class UserConverterTest {

    @Test
    public void convert() throws Exception {
       User user = new User();
       user.setId("12");
       user.setDevices(new HashSet<>(Arrays.asList("IPHONE", "SAMSUNG")));

       verify(user, UserResponse.builder(user) );
    }

    private void verify(final User user, final UserResponse userResponse) {
        assertThat(user.getId()).isEqualTo(userResponse.getId());
        assertThat(user.getDevices()).isEqualTo(userResponse.getDevices());
    }


}