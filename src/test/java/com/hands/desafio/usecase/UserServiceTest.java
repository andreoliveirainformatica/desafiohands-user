package com.hands.desafio.usecase;

import com.hands.desafio.entity.User;
import com.hands.desafio.gateway.InMemoryUserGateway;
import com.hands.desafio.http.dto.UserResponse;
import com.hands.desafio.usecase.convert.csv.UserConverter;
import com.hands.desafio.usecase.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {

    private UserService userService;

    private InMemoryUserGateway userGateway = new InMemoryUserGateway();


    @Before
    public void setup() {
        userService = new UserServiceImpl(userGateway, new UserConverter());
        userGateway.clearCache();
    }

    @Test
    public void getDeviceByUserById() throws Exception {
        populateCache();
        UserResponse userResponse = userService.findById("5049527c-070b-46d3-9109-85bf9b7e8b53");

        User user = new User();
        user.setId("5049527c-070b-46d3-9109-85bf9b7e8b53");
        user.setDevices(new HashSet<>(Arrays.asList("IPHONE", "SAMSUNG")));

        verify(user, userResponse);
    }

    private void verify(final User user, final UserResponse userResponse) {
        assertThat(user.getId()).isEqualTo(userResponse.getId());
        assertThat(user.getDevices()).isEqualTo(userResponse.getDevices());
    }

    private void populateCache() {
        String line = "5049527c-070b-46d3-9109-85bf9b7e8b53;IPHONE";
        String line2 = "5049527c-070b-46d3-9109-85bf9b7e8b53;SAMSUNG";

        userService.importUsers(new String[] {line, line2});
    }



}