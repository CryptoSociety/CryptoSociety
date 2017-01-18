package com.codeup.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Cryptos extends CrudRepository<Crypto, Long> {
    public List<Crypto> findByActiveEquals(Boolean bol);
}