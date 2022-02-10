package com.example.firstproject.repository;

import com.example.firstproject.entity.Members;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Members,String> {
    @Override
    List<Members> findAll();

    List<Members> findAll(Sort id);
}
