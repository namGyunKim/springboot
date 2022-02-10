package com.example.firstproject.controller;

import com.example.firstproject.entity.Members;
import com.example.firstproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminsController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("members/view")
    public String membersView(Model model){
//        모든 members목록을 가져온다
        List<Members> membersList = memberRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        가져온 Members묶음을 뷰로 전달할 변수 설정
        model.addAttribute("membersList", membersList);
//        뷰 페이지 설정
        return "member/index";
    }
}
