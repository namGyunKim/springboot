package com.example.firstproject.repository;

import com.example.firstproject.entity.CGarbage;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoffeeGarbageRepository extends CrudRepository<CGarbage,Long> {
    @Override
    List<CGarbage> findAll();

    List<CGarbage> findAll(Sort id);
}
