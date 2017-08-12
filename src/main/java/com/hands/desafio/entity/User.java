package com.hands.desafio.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Getter
@Setter
@ToString
public class User {

    @Id
    private String id;

    private String name;

    private Set<String> devices;
}
