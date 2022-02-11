package com.example.firstproject.controller;

import com.example.firstproject.entity.Cbasket;
import com.example.firstproject.entity.Coffees;
import com.example.firstproject.repository.BasketRepository;
import com.example.firstproject.repository.CoffeeRepository;
import com.example.firstproject.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
public class PayController {

    @Autowired
    BasketRepository basketRepository;
    @Autowired
    CoffeeRepository coffeeRepository;

    @Autowired
    PayService payService;

    @GetMapping("/coffee/basket/{id}")
    public String basket(@PathVariable Long id, HttpSession session){
        payService.addBasket(id,session);
        return "redirect:/coffees/order";
    }

    @GetMapping("/coffee/basket/tea/{id}")
    public String basketTea(@PathVariable Long id, HttpSession session,RedirectAttributes rttr){
        payService.addTea(id,session);

        return "redirect:/coffees/order";
    }
    @GetMapping("/coffee/basket/latte/{id}")
    public String basketLatte(@PathVariable Long id, HttpSession session,RedirectAttributes rrtr){
            payService.addLatte(id, session);
        return "redirect:/coffees/order";
    }
    @GetMapping("/coffee/basket/ade/{id}")
    public String basketAde(@PathVariable Long id, HttpSession session,RedirectAttributes rrtr){
        payService.addAde(id, session);
        return "redirect:/coffees/order";
    }


    @GetMapping("/coffee/basketorder/{id}")
    public String order(@PathVariable String id, Model model){
        payService.basketOrder(id,model);
        return "pay/basket";
    }

    @GetMapping("/coffee/basket/delete/{uid}/{id}")
    public String delete(@PathVariable String uid,Model model,@PathVariable Long id){
        payService.basketDelete(uid, model, id);
        return "redirect:/coffee/basketorder/"+uid;
    }

    @GetMapping("/coffee/basket/add/{uid}/{id}")
    public String add(@PathVariable String uid,Model model,@PathVariable Long id){
        payService.basketSum(uid, model, id);
        return "redirect:/coffee/basketorder/"+uid;
    }

    @GetMapping("/order/result/{uid}")
    public String orderResult(@PathVariable String uid, Model model, HttpServletResponse response,HttpSession httpSession){
        payService.basketPayment(uid, model, response, httpSession);
        return "/pay/kakao";
    }

}
