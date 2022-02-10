package com.example.firstproject.service;

import com.example.firstproject.entity.CGarbage;
import com.example.firstproject.entity.Coffees;
import com.example.firstproject.repository.CoffeeGarbageRepository;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayService {

    @Autowired
    CoffeeGarbageRepository garbageRepository;
    @Autowired
    CoffeeRepository coffeeRepository;

    public void recovery(Long id){
//      복구할 데이터 가져오기
        CGarbage cGarbageEntity = garbageRepository.findById(id).orElse(null);
        log.info(String.valueOf(cGarbageEntity));
//        휴지통에서 삭제
        garbageRepository.deleteById(id);
//        상품 목록에 복구
        Coffees coffeesEntity = new Coffees(id, cGarbageEntity.getTitle(), cGarbageEntity.getContent(), cGarbageEntity.getPrice());
        log.info(String.valueOf(coffeesEntity));
        Coffees saved = coffeeRepository.save(coffeesEntity);
    }
}
