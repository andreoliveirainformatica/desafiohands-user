package com.hands.desafio.http;

import com.hands.desafio.UserApplication;
import com.hands.desafio.entity.User;
import com.hands.desafio.gateway.InMemoryUserGateway;
import com.hands.desafio.http.error.ExceptionTranslator;
import com.hands.desafio.usecase.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class UserControllerIntTest {

    @Autowired
    private UserService userService;

    private InMemoryUserGateway userGateway = new InMemoryUserGateway();

    private MockMvc restUserMockMvc;


    @Before
    public void setup() {
        UserController userController = new UserController(userService);
        ReflectionTestUtils.setField(userService, "userGateway",  userGateway);

        final StaticApplicationContext applicationContext = new StaticApplicationContext();
        applicationContext.registerSingleton("exceptionHandler", ExceptionTranslator.class);
        final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
        webMvcConfigurationSupport.setApplicationContext(applicationContext);

        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver())
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();

        userGateway.clearCache();
    }



    @Test
    public void getAllUsers() throws Exception {
        populateCache();
        restUserMockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem("12")))
                .andExpect(jsonPath("$.[*].id").value(hasItem("22")));
    }

    @Test
    public void getUsersById() throws Exception {
        populateCache();
        restUserMockMvc.perform(get("/api/users/12/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(is("12")));
    }

    @Test
    public void getUsersByIdNotFound() throws Exception {
        populateCache();
        restUserMockMvc.perform(get("/api/users/13/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());
    }


    private void populateCache() {
        User user = new User();
        user.setId("12");
        user.setDevices(new HashSet<>(Arrays.asList("IPHONE", "SAMSUNG")));

        User user2 = new User();
        user2.setId("22");
        user2.setDevices(new HashSet<>(Arrays.asList("MOTOROLA")));

        userGateway.save(user);
        userGateway.save(user2);
    }



}