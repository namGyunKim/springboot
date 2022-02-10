package com.example.firstproject.controller;

import com.example.firstproject.entity.CGarbage;
import com.example.firstproject.entity.Coffees;
import com.example.firstproject.repository.CoffeeGarbageRepository;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class CoffeeGarbageController {

    @Autowired
    CoffeeGarbageRepository garbageRepository;
    @Autowired
    CoffeeRepository coffeeRepository;

    @GetMapping("/garbage/coffee")
    public String coffeeGarbage(Model model){
        //        모든 상품 목록을 가져온다
        List<CGarbage> coffeesList = garbageRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
//        뷰 페이지 설정
        return "garbage/coffeeIndex";
    }

    @GetMapping("/garbage/coffee/recovery/{id}")
    public String coffeeGarbageRecovery(@PathVariable Long id, RedirectAttributes rttr) {
//      복구할 데이터 가져오기
        CGarbage cGarbageEntity = garbageRepository.findById(id).orElse(null);
        log.info(String.valueOf(cGarbageEntity));
//        휴지통에서 삭제
        garbageRepository.deleteById(id);
//        상품 목록에 복구
        Coffees coffeesEntity = new Coffees(id, cGarbageEntity.getTitle(), cGarbageEntity.getContent(), cGarbageEntity.getPrice());
        log.info(String.valueOf(coffeesEntity));
        Coffees saved = coffeeRepository.save(coffeesEntity);
        rttr.addFlashAttribute("msg", id + "번 상품이 복구됨");
//        뷰 페이지 설정
        return "redirect:/garbage/coffee";
    }
}
