package com.example.firstproject.repository;

import com.example.firstproject.entity.Coffees;
import com.example.firstproject.entity.Tea;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeaRepository extends CrudRepository<Tea,Long> {
    @Override
    List<Tea> findAll();

    List<Tea> findAll(Sort id);
}
