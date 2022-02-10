package com.example.firstproject.repository;

import com.example.firstproject.entity.Coffees;
import com.example.firstproject.entity.Latte;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LatteRepository extends CrudRepository<Latte,Long> {
    @Override
    List<Latte> findAll();

    List<Latte> findAll(Sort id);
}
