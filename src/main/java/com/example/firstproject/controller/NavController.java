package com.example.firstproject.controller;

import com.example.firstproject.entity.Ade;
import com.example.firstproject.entity.Coffees;
import com.example.firstproject.entity.Latte;
import com.example.firstproject.entity.Tea;
import com.example.firstproject.repository.AdeRepository;
import com.example.firstproject.repository.CoffeeRepository;
import com.example.firstproject.repository.LatteRepository;
import com.example.firstproject.repository.TeaRepository;
import com.example.firstproject.service.NavService;
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

    @Autowired
    NavService navService;

    @GetMapping("/coffee/ade")
    public String ade(Model model){
        navService.adeFindAll(model);
        return "coffee/ade";
    }

    @GetMapping("/coffee/tea")
    public String tea(Model model){
        navService.teaFindAll(model);
//        뷰 페이지 설정
        return "coffee/tea";
    }

    @GetMapping("/coffee/latte")
    public String latte(Model model){
        navService.latteFindAll(model);
//        뷰 페이지 설정
        return "coffee/latte";
    }

    @GetMapping("/addade/{id}")
    public String addAde(Model model, RedirectAttributes rttr, @PathVariable Long id){
        navService.addAde(model, id);
        rttr.addFlashAttribute("msg", "에이드에 추가");
//        뷰 페이지 설정
        return "redirect:/coffees/index";
    }
    @GetMapping("/addtea/{id}")
    public String addTea(Model model,RedirectAttributes rttr,@PathVariable Long id){

        navService.addTea(model,id);
        rttr.addFlashAttribute("msg", "티에 추가");
//        뷰 페이지 설정
        return "redirect:/coffees/index";
    }

    @GetMapping("/addlatte/{id}")
    public String addLatte(Model model,RedirectAttributes rttr,@PathVariable Long id){
        navService.addLatte(model, id);
        rttr.addFlashAttribute("msg", "라떼에 추가");
//        뷰 페이지 설정
        return "redirect:/coffees/index";
    }

}
