package com.codeup.models;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Cryptos extends CrudRepository<Crypto, Long> {
    List<Crypto> findByActiveEqualsAndIsApprovedEquals(boolean active, boolean approved);
    List<Crypto> findFirst3ByActiveEqualsAndIsApprovedEqualsOrderByCreationDateDesc(boolean active, boolean approved);
}