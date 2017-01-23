package com.codeup.auth;

import org.springframework.data.repository.CrudRepository;

public interface Users extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}