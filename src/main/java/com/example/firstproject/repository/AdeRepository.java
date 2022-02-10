package com.example.firstproject.repository;

import com.example.firstproject.entity.Ade;
import com.example.firstproject.entity.Coffees;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdeRepository extends CrudRepository<Ade,Long> {
    @Override
    List<Ade> findAll();

    List<Ade> findAll(Sort id);
}
