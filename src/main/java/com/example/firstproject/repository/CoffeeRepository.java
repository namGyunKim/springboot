package com.example.firstproject.repository;

import com.example.firstproject.entity.Coffees;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoffeeRepository extends CrudRepository<Coffees,Long> {
    @Override
    List<Coffees> findAll();

    List<Coffees> findAll(Sort id);
}
