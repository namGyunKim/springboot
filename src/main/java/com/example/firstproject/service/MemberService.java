package com.example.firstproject.service;


import com.example.firstproject.dto.MemberDto;
import com.example.firstproject.dto.PassDto;
import com.example.firstproject.entity.Members;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public String MemberJoin(MemberDto dto, PassDto passDto) {
        String msg;
//            dto를 Entity로 변환
        Members members = dto.toEntity();
        log.info(String.valueOf(dto));
        log.info(String.valueOf(members));
//            Repository에게 Entity를 저장하게함
        Members save = memberRepository.save(members);
        log.info(String.valueOf(save));
        msg = "회원가입을 축하드립니다";
        return msg;
    }

    public String MemberLogin(Model model, MemberDto dto, HttpSession session) {
        String msg;
        String adminsId = "admin@admin.com";
        String adminsPassword = "1234";
        String strReturn;
        //        관리자 계정이면
        if (dto.getId().equals(adminsId) && dto.getPassword().equals(adminsPassword)) {
            session.setAttribute("admin", dto.getId());
            session.setAttribute("userId", dto.getId());
            strReturn="member/admins";
        } else if (memberRepository.existsById(dto.getId())) {
            Members membersEntity = memberRepository.findById(dto.getId()).orElse(null);
            log.info(String.valueOf(membersEntity));
//            알람창추가위한것
            if (membersEntity.getPassword().equals(dto.getPassword())) {

                log.info(String.valueOf(dto));
//                로그인 세션 추가
                session.setAttribute("loginMsg", "ok");
                session.setAttribute("userId", dto.getId());
                session.setAttribute("userPassword", dto.getPassword());
                strReturn="redirect:/hi";
            } else {
                session.setAttribute("rttrMsg","비밀번호가 틀렸습니다");
                log.info(String.valueOf(dto));
                strReturn="redirect:/login";
            }
        } else {
            session.setAttribute("rttrMsg","아이디가 틀렸씁니다");
            log.info(String.valueOf(dto));
            strReturn="redirect:/login";
        }
        return strReturn;
    }
}