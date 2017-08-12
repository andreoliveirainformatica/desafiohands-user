package com.hands.desafio.gateway.repository;

import com.hands.desafio.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by andre.oliveira on 8/11/17.
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {

}
