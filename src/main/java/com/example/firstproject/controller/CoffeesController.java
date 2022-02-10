package com.example.firstproject.controller;


import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.*;
import com.example.firstproject.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
public class CoffeesController {

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

    @GetMapping("/coffees/new")
    public String coffeesNew(){
        return "coffee/new";
    }

    @PostMapping("/coffees/new2")
    public String coffeesNew2(CoffeeDto dto, RedirectAttributes rttr){
//        dto를 Entity로 변환
        Coffees coffees=dto.toEntity();
        log.info(String.valueOf(dto));
        log.info(String.valueOf(coffees));
//        Repository에게 Entity를 저장하게함
        Coffees saved = coffeeRepository.save(coffees);
        log.info(String.valueOf(saved));
        rttr.addFlashAttribute("msg", "상품등록 완료");
        return "coffee/new";

    }

    @GetMapping("/coffees/index")
    public String coffeesIndex(Model model){
//        모든 상품 목록을 가져온다
        List<Coffees> coffeesList = coffeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
//        뷰 페이지 설정
        return "coffee/index";
    }

    @GetMapping("/coffees/{id}")
    public String edit(@PathVariable Long id,Model model){
//        수정할 데이터 가져오기
        Coffees coffeesEntity=coffeeRepository.findById(id).orElse(null);
//        모델에 데이터 등록
        model.addAttribute("coffee",coffeesEntity);
//        뷰 페이지 설정
        return "coffee/edit";
    }

    @PostMapping("/coffees/update")
    public String update(CoffeeDto dto,RedirectAttributes rttr){
//        DTO를 엔티티로 변환
        Coffees coffesEntity = dto.toEntity();
//        엔티티를 DB에 저장
        Coffees target = coffeeRepository.findById(coffesEntity.getId()).orElse(null);  //데이터가 없으면 null
        if(target!=null){
            coffeeRepository.save(coffesEntity);
            rttr.addFlashAttribute("msg", dto.getId() + "번상품의 정보가 변경되었습니다.");
        }
//        전체 상품 페이지로 리다이랙트
        return "redirect:/coffees/index";

    }

    @GetMapping("/coffees/delete")
    public String delete(Model model){
        //        모든 상품 목록을 가져온다
        List<Coffees> coffeesList = coffeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 상품목록을 뷰로 전달
        model.addAttribute("coffeesList", coffeesList);
//        뷰 페이지 설정
        return "coffee/delete";
    }

    @GetMapping("/coffees/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes rttr){
//        삭제할 데이터 가져오기
        Coffees coffeesEntity=coffeeRepository.findById(id).orElse(null);
        log.info(String.valueOf(coffeesEntity));
//        상품 삭제
        coffeeRepository.deleteById(id);
//        휴지통에 추가
        CGarbage cGarbageEntity = new CGarbage(id, coffeesEntity.getTitle(), coffeesEntity.getContent(), coffeesEntity.getPrice());
        log.info(String.valueOf(cGarbageEntity));
        CGarbage saved = garbageRepository.save(cGarbageEntity);
        rttr.addFlashAttribute("msg", id + "번상품의 데이터가 삭제되었습니다.");
//        카테고리에서 삭제
        if(teaRepository.existsById(id)) teaRepository.deleteById(id);
        if(adeRepository.existsById(id)) adeRepository.deleteById(id);
        if(latteRepository.existsById(id)) adeRepository.deleteById(id);
//        뷰 페이지 설정
        return "redirect:/coffees/delete";
    }

@GetMapping("/coffees/order")
public String order(Model model, HttpSession httpSession){

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
