package com.example.firstproject.repository;

import com.example.firstproject.entity.Cbasket;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BasketRepository extends JpaRepository<Cbasket,Long> {
    @Override
    List<Cbasket> findAll();

    List<Cbasket> findAll(Sort id);

    @Query(value = "select * from cbasket where userid= :userid",
            nativeQuery = true)
    List<Cbasket> orderUser(@Param("userid") String userid);

}
