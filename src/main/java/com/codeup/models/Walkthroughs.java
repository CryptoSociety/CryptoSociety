package com.codeup.models;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Walkthroughs extends CrudRepository<Walkthrough, Long> {
    public List<Walkthrough> findAllByOrderByDifficultyAsc();
    public Walkthrough findFirstByScheme(String scheme);
}
