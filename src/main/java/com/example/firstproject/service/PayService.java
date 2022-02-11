package com.example.firstproject.service;

import com.example.firstproject.entity.CGarbage;
import com.example.firstproject.entity.Cbasket;
import com.example.firstproject.entity.Coffees;
import com.example.firstproject.repository.BasketRepository;
import com.example.firstproject.repository.CoffeeGarbageRepository;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Slf4j
public class PayService {

    @Autowired
    CoffeeGarbageRepository garbageRepository;
    @Autowired
    CoffeeRepository coffeeRepository;

    @Autowired
    BasketRepository basketRepository;

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
    public void addBasket(Long id, HttpSession session){
        String userId=(String)session.getAttribute("userId");
        log.info(userId);
//      entity 생성
        Coffees coffeesEntity = coffeeRepository.findById(id).orElse(null);
        log.info(String.valueOf(coffeesEntity));
        //존재한다면 가격 증가
        if(basketRepository.existsById(id)){
            Cbasket cbasket = basketRepository.findById(id).orElse(null);
            Cbasket cbasketEntity = new Cbasket(userId,id, coffeesEntity.getTitle(), coffeesEntity.getContent(), cbasket.getPrice());
            log.info(String.valueOf(cbasketEntity));
            cbasketEntity.setPrice(cbasketEntity.getPrice()+coffeesEntity.getPrice());
            log.info(String.valueOf(cbasketEntity));
            Cbasket saved = basketRepository.save(cbasketEntity);
            log.info(String.valueOf(saved));
        }
//        장바구니에 존재하지 않는다면 저장
        else {
            Cbasket cbasketEntity = new Cbasket(userId,id, coffeesEntity.getTitle(), coffeesEntity.getContent(), coffeesEntity.getPrice());
            log.info(String.valueOf(cbasketEntity));
            Cbasket saved = basketRepository.save(cbasketEntity);
            log.info(String.valueOf(saved));
        }
    }

    public void addTea(Long id,HttpSession session){
        String userId=(String)session.getAttribute("userId");
        log.info(userId);
//      entity 생성
        Coffees coffeesEntity = coffeeRepository.findById(id).orElse(null);
        log.info(String.valueOf(coffeesEntity));
        //존재한다면 가격 증가
        if(basketRepository.existsById(id)){
            Cbasket cbasket = basketRepository.findById(id).orElse(null);
            Cbasket cbasketEntity = new Cbasket(userId,id, coffeesEntity.getTitle(), coffeesEntity.getContent(), cbasket.getPrice());
            log.info(String.valueOf(cbasketEntity));
            cbasketEntity.setPrice(cbasketEntity.getPrice()+coffeesEntity.getPrice());
            log.info(String.valueOf(cbasketEntity));
            Cbasket saved = basketRepository.save(cbasketEntity);
            log.info(String.valueOf(saved));
        }
//        장바구니에 존재하지 않는다면 저장
        else {
            Cbasket cbasketEntity = new Cbasket(userId,id, coffeesEntity.getTitle(), coffeesEntity.getContent(), coffeesEntity.getPrice());
            log.info(String.valueOf(cbasketEntity));
            Cbasket saved = basketRepository.save(cbasketEntity);
            log.info(String.valueOf(saved));
        }
        session.setAttribute("kate","tea");
    }

    public void addLatte(Long id,HttpSession session){
        String userId=(String)session.getAttribute("userId");
        log.info(userId);
//      entity 생성
        Coffees coffeesEntity = coffeeRepository.findById(id).orElse(null);
        log.info(String.valueOf(coffeesEntity));
        //존재한다면 가격 증가
        if(basketRepository.existsById(id)){
            Cbasket cbasket = basketRepository.findById(id).orElse(null);
            Cbasket cbasketEntity = new Cbasket(userId,id, coffeesEntity.getTitle(), coffeesEntity.getContent(), cbasket.getPrice());
            log.info(String.valueOf(cbasketEntity));
            cbasketEntity.setPrice(cbasketEntity.getPrice()+coffeesEntity.getPrice());
            log.info(String.valueOf(cbasketEntity));
            Cbasket saved = basketRepository.save(cbasketEntity);
            log.info(String.valueOf(saved));
        }
//        장바구니에 존재하지 않는다면 저장
        else {
            Cbasket cbasketEntity = new Cbasket(userId,id, coffeesEntity.getTitle(), coffeesEntity.getContent(), coffeesEntity.getPrice());
            log.info(String.valueOf(cbasketEntity));
            Cbasket saved = basketRepository.save(cbasketEntity);
            log.info(String.valueOf(saved));
        }
        session.setAttribute("kate","latte");
    }

    public void addAde(Long id,HttpSession session){
        String userId=(String)session.getAttribute("userId");
        log.info(userId);
//      entity 생성
        Coffees coffeesEntity = coffeeRepository.findById(id).orElse(null);
        log.info(String.valueOf(coffeesEntity));
        //존재한다면 가격 증가
        if(basketRepository.existsById(id)){
            Cbasket cbasket = basketRepository.findById(id).orElse(null);
            Cbasket cbasketEntity = new Cbasket(userId,id, coffeesEntity.getTitle(), coffeesEntity.getContent(), cbasket.getPrice());
            log.info(String.valueOf(cbasketEntity));
            cbasketEntity.setPrice(cbasketEntity.getPrice()+coffeesEntity.getPrice());
            log.info(String.valueOf(cbasketEntity));
            Cbasket saved = basketRepository.save(cbasketEntity);
            log.info(String.valueOf(saved));
        }
//        장바구니에 존재하지 않는다면 저장
        else {
            Cbasket cbasketEntity = new Cbasket(userId,id, coffeesEntity.getTitle(), coffeesEntity.getContent(), coffeesEntity.getPrice());
            log.info(String.valueOf(cbasketEntity));
            Cbasket saved = basketRepository.save(cbasketEntity);
            log.info(String.valueOf(saved));
        }

        session.setAttribute("kate","ade");
    }

    public void basketOrder(String id, Model model){
        List<Cbasket> cbasketList=basketRepository.orderUser(id);
        log.info(String.valueOf(cbasketList));
        model.addAttribute("basketList", cbasketList);
    }

    public void basketDelete(String uid,Model model,Long id){
        //      리스트 가져옴
        List<Cbasket> cbasketList=basketRepository.orderUser(uid);
        Cbasket cbasket = basketRepository.findById(id).orElse(null);
        Coffees coffees = coffeeRepository.findById(id).orElse(null);
        int price=0;
        int price2=coffees.getPrice();
        log.info(String.valueOf(cbasket));
        log.info(String.valueOf(cbasketList));
        log.info(String.valueOf(id));
//        해당 상품 -1
        if(cbasket.getPrice() >coffees.getPrice()){
            for(int i=0;i<cbasketList.size();i++){
                if(cbasketList.get(i).getUserid().equals(uid) && cbasketList.get(i).getId().equals(id)){
                    price=cbasketList.get(i).getPrice();
                    Cbasket saved=new Cbasket(uid, id, cbasketList.get(i).getTitle(), cbasketList.get(i).getContent(), price - price2);
                    basketRepository.save(saved);
                    log.info(String.valueOf(price));
                    log.info(String.valueOf(price2));
                    log.info(String.valueOf(saved));
                }
            }
        }
        else {
            basketRepository.deleteById(id);
        }

        model.addAttribute("basketList", cbasketList);
    }

    public void basketSum(String uid,Model model,Long id){
        //      리스트 가져옴
        List<Cbasket> cbasketList=basketRepository.orderUser(uid);
        Coffees coffees = coffeeRepository.findById(id).orElse(null);
        int price=0;
        int price2=coffees.getPrice();

        for(int i=0;i<cbasketList.size();i++){
            if(cbasketList.get(i).getUserid().equals(uid) && cbasketList.get(i).getId().equals(id)){
                price=cbasketList.get(i).getPrice();
                Cbasket saved=new Cbasket(uid, id, cbasketList.get(i).getTitle(), cbasketList.get(i).getContent(), price + price2);
                basketRepository.save(saved);
                log.info(String.valueOf(price));
                log.info(String.valueOf(price2));
                log.info(String.valueOf(saved));
            }
        }

        model.addAttribute("basketList", cbasketList);
    }

    public void basketPayment(String uid, Model model, HttpServletResponse response,HttpSession httpSession){
        //      리스트 가져옴
        List<Cbasket> cbasketList=basketRepository.orderUser(uid);
        log.info(String.valueOf(cbasketList));
        int sum=0;
        for (int i=0;i<cbasketList.size();i++){
            sum+= cbasketList.get(i).getPrice();
        }
//        userId세션 받아옴
        String payUserId= (String) httpSession.getAttribute("userId");


        //userid쿠키생성
        Cookie cookie2=new Cookie("userID",String.valueOf(payUserId));
        cookie2.setMaxAge(60*60*24);
        response.addCookie(cookie2);

        //쿠키생성
        Cookie cookie=new Cookie("totalsum3",String.valueOf(sum));
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
    }

}
