package com.example.firstproject.service;

import com.example.firstproject.entity.Ade;
import com.example.firstproject.entity.Coffees;
import com.example.firstproject.entity.Latte;
import com.example.firstproject.entity.Tea;
import com.example.firstproject.repository.AdeRepository;
import com.example.firstproject.repository.CoffeeRepository;
import com.example.firstproject.repository.LatteRepository;
import com.example.firstproject.repository.TeaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@Slf4j
public class NavService {
    @Autowired
    AdeRepository adeRepository;
    @Autowired
    LatteRepository latteRepository;
    @Autowired
    TeaRepository teaRepository;
    @Autowired
    CoffeeRepository coffeeRepository;

    public void adeFindAll(Model model){
        //        모든 상품을 가져온다
        List<Ade> coffeesList = adeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
    }

    public void teaFindAll(Model model){
        //        모든 상품 목록을 가져온다
        List<Tea> coffeesList = teaRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
    }

    public void latteFindAll(Model model){
        //        모든 상품 목록을 가져온다
        List<Latte> coffeesList = latteRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
    }

    public void addAde(Model model,Long id){
        //        추가할 상품을 가져온다
        Coffees coffeesEntity = coffeeRepository.findById(id).orElse(null);
        log.info(String.valueOf(coffeesEntity));
//        카테고리에 추가
        Ade adeEntity = new Ade(id, coffeesEntity.getTitle(), coffeesEntity.getContent(), coffeesEntity.getPrice());
        log.info(String.valueOf(adeEntity));
        Ade saved = adeRepository.save(adeEntity);
        log.info(String.valueOf(saved));
//        다른 카테고리에서 삭제
        if(teaRepository.existsById(id)) teaRepository.deleteById(id);
        if(latteRepository.existsById(id)) latteRepository.deleteById(id);

//        모든 상품 목록을 가져온다
        List<Coffees> coffeesList = coffeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
    }

    public void addTea(Model model,Long id){
        //        추가할 상품을 가져온다
        Coffees coffeesEntity = coffeeRepository.findById(id).orElse(null);

//        카테고리에 추가
        Tea teaEntity = new Tea(id, coffeesEntity.getTitle(), coffeesEntity.getContent(), coffeesEntity.getPrice());
        Tea saved = teaRepository.save(teaEntity);
//        다른 카테고리에서 삭제
        if(adeRepository.existsById(id)) adeRepository.deleteById(id);
        if(latteRepository.existsById(id)) latteRepository.deleteById(id);

//        모든 상품 목록을 가져온다
        List<Coffees> coffeesList = coffeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
    }

    public void addLatte(Model model,Long id){
        //        추가할 상품을 가져온다
        Coffees coffeesEntity = coffeeRepository.findById(id).orElse(null);

//        카테고리에 추가
        Latte latteEntity = new Latte(id, coffeesEntity.getTitle(), coffeesEntity.getContent(), coffeesEntity.getPrice());
        Latte saved = latteRepository.save(latteEntity);
//        다른 카테고리에서 삭제
        if(teaRepository.existsById(id)) teaRepository.deleteById(id);
        if(adeRepository.existsById(id)) adeRepository.deleteById(id);

//        모든 상품 목록을 가져온다
        List<Coffees> coffeesList = coffeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
    }

}
