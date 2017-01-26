package com.codeup.auth;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Users extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
    List<User> findTop10ByOrderByPointsDesc();
}