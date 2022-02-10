package com.example.firstproject.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class NavController {

    @Autowired
    AdeRepository adeRepository;
    @Autowired
    LatteRepository latteRepository;
    @Autowired
    TeaRepository teaRepository;
    @Autowired
    CoffeeRepository coffeeRepository;


    @GetMapping("/coffee/ade")
    public String ade(Model model){
//        모든 상품을 가져온다
        List<Ade> coffeesList = adeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
        return "coffee/ade";
    }

    @GetMapping("/coffee/tea")
    public String tea(Model model){
//        모든 상품 목록을 가져온다
        List<Tea> coffeesList = teaRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
//        뷰 페이지 설정
        return "coffee/tea";
    }

    @GetMapping("/coffee/latte")
    public String latte(Model model){
//        모든 상품 목록을 가져온다
        List<Latte> coffeesList = latteRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
//        뷰 페이지 설정
        return "coffee/latte";
    }

    @GetMapping("/addade/{id}")
    public String addAde(Model model, RedirectAttributes rttr, @PathVariable Long id){

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


        rttr.addFlashAttribute("msg", "에이드에 추가");

//        모든 상품 목록을 가져온다
        List<Coffees> coffeesList = coffeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
//        뷰 페이지 설정
        return "redirect:/coffees/index";
    }
    @GetMapping("/addtea/{id}")
    public String addTea(Model model,RedirectAttributes rttr,@PathVariable Long id){

        //        추가할 상품을 가져온다
        Coffees coffeesEntity = coffeeRepository.findById(id).orElse(null);

//        카테고리에 추가
        Tea teaEntity = new Tea(id, coffeesEntity.getTitle(), coffeesEntity.getContent(), coffeesEntity.getPrice());
        Tea saved = teaRepository.save(teaEntity);
//        다른 카테고리에서 삭제
        if(adeRepository.existsById(id)) adeRepository.deleteById(id);
        if(latteRepository.existsById(id)) latteRepository.deleteById(id);


        rttr.addFlashAttribute("msg", "티에 추가");

//        모든 상품 목록을 가져온다
        List<Coffees> coffeesList = coffeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
//        뷰 페이지 설정
        return "redirect:/coffees/index";
    }

    @GetMapping("/addlatte/{id}")
    public String addLatte(Model model,RedirectAttributes rttr,@PathVariable Long id){

        //        추가할 상품을 가져온다
        Coffees coffeesEntity = coffeeRepository.findById(id).orElse(null);

//        카테고리에 추가
        Latte latteEntity = new Latte(id, coffeesEntity.getTitle(), coffeesEntity.getContent(), coffeesEntity.getPrice());
        Latte saved = latteRepository.save(latteEntity);
//        다른 카테고리에서 삭제
        if(teaRepository.existsById(id)) teaRepository.deleteById(id);
        if(adeRepository.existsById(id)) adeRepository.deleteById(id);


        rttr.addFlashAttribute("msg", "라떼에 추가");


//        모든 상품 목록을 가져온다
        List<Coffees> coffeesList = coffeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
//        뷰 페이지 설정
        return "redirect:/coffees/index";
    }

}
