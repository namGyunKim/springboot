package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.*;
import com.example.firstproject.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Slf4j
public class CoffeeService {
    @Autowired
    CoffeeRepository coffeeRepository;
    @Autowired
    CoffeeGarbageRepository garbageRepository;
    @Autowired
    AdeRepository adeRepository;
    @Autowired
    TeaRepository teaRepository;
    @Autowired
    LatteRepository latteRepository;

    public void addCoffee(CoffeeDto dto){
        //        dto를 Entity로 변환
        Coffees coffees=dto.toEntity();
        log.info(String.valueOf(dto));
        log.info(String.valueOf(coffees));
//        Repository에게 Entity를 저장하게함
        Coffees saved = coffeeRepository.save(coffees);
        log.info(String.valueOf(saved));
    }

    public void CoffeeKateDelete(Long id){
        //        삭제할 데이터 가져오기
        Coffees coffeesEntity=coffeeRepository.findById(id).orElse(null);
        log.info(String.valueOf(coffeesEntity));
//        상품 삭제
        coffeeRepository.deleteById(id);
//        휴지통에 추가
        CGarbage cGarbageEntity = new CGarbage(id, coffeesEntity.getTitle(), coffeesEntity.getContent(), coffeesEntity.getPrice());
        log.info(String.valueOf(cGarbageEntity));
        CGarbage saved = garbageRepository.save(cGarbageEntity);
//        카테고리에서 삭제
        if(teaRepository.existsById(id)) teaRepository.deleteById(id);
        if(adeRepository.existsById(id)) adeRepository.deleteById(id);
        if(latteRepository.existsById(id)) adeRepository.deleteById(id);
    }

    public String CoffeeOrder(Model model,HttpSession httpSession){

        log.info("오더메소드 도착");
        String kate = String.valueOf(httpSession.getAttribute("kate"));
        log.info(kate);

        //        모든 상품 목록을 가져온다
        if (kate.equals("tea")){
            log.info("티에서 누른경우");
            List<Tea> coffeesList = teaRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
            log.info(String.valueOf(coffeesList));
//        가져온 상품목록을 뷰로 전달
            model.addAttribute("coffeesList", coffeesList);
            //        뷰 페이지 설정
            return "coffee/tea";
        }
        else if(kate.equals("latte")){
            log.info("라떼에서 누른경우");
            List<Latte> coffeesList = latteRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
            log.info(String.valueOf(coffeesList));
//        가져온 상품목록을 뷰로 전달
            model.addAttribute("coffeesList", coffeesList);
            //        뷰 페이지 설정
            return "coffee/latte";
        }
        else if(kate.equals("ade")){
            log.info("에이드에서 누른경우");
            List<Ade> coffeesList = adeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
            log.info(String.valueOf(coffeesList));
//        가져온 상품목록을 뷰로 전달
            model.addAttribute("coffeesList", coffeesList);
            //        뷰 페이지 설정
            return "coffee/ade";
        }
        else{
            List<Coffees> coffeesList = coffeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
            log.info(String.valueOf(coffeesList));
//        가져온 상품목록을 뷰로 전달
            model.addAttribute("coffeesList", coffeesList);
//        뷰 페이지 설정
            return "coffee/order";
        }
    }

}
