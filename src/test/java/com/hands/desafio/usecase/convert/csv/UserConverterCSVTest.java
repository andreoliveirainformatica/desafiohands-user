package com.hands.desafio.usecase.convert.csv;

import com.hands.desafio.entity.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by andre.oliveira on 8/11/17.
 */
public class UserConverterCSVTest {

    private UserConverter converter;

    @Before
    public void setup() {
        converter = new UserConverter();
    }

    @Test
    public void converterTest() {
        String line = "5049527c-070b-46d3-9109-85bf9b7e8b53;IPHONE";

        User user = new User();
        user.setId("5049527c-070b-46d3-9109-85bf9b7e8b53");
        user.setDevices(new HashSet<>(Arrays.asList("IPHONE")));

        final User userConverted = converter.convert(line);

        verify(user, userConverted);
    }

    private void verify(final User user, final User userCovented) {
        assertThat(user.getId()).isEqualTo(userCovented.getId());
        assertThat(user.getDevices()).isEqualTo(userCovented.getDevices());
    }
}