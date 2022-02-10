package com.example.firstproject.service;


import com.example.firstproject.dto.MemberDto;
import com.example.firstproject.dto.PassDto;
import com.example.firstproject.entity.Members;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    public void MemberEdit(MemberDto dto,HttpSession session){

        String id=(String)session.getAttribute("userId");
        log.info(id);
        session.removeAttribute("userPassword");

//        DTO를 엔티티로 변환
        Members members =dto.toEntity();
//        엔티티를 DB로 저장
        Members target = memberRepository.findById(id).orElse(null);    //데이터가 없으면 null
        if(target!=null){
            memberRepository.save(members);
            session.setAttribute("rttrMsg","개인정보 수정 완료");
            session.setAttribute("userPassword",dto.getPassword());
        }
    }

    public boolean SessionExist(String str,HttpSession httpSession){
        if (httpSession.getAttribute(str)!=null) return true;
        else return false;
    }

    public void MemberDelete(String id,HttpSession session){
        //        삭제할 데이터 가져오기
        Members membersEntity = memberRepository.findById(id).orElse(null);
        log.info(String.valueOf(membersEntity));
//        데이터 삭제
        memberRepository.deleteById(id);
//        세션 삭제
        session.removeAttribute("userId");
        session.removeAttribute("userPassword");
        session.removeAttribute("admin");
        session.setAttribute("deleteMsg","ok");
    }

    public void MemberIdCheck(PassDto dto,HttpSession httpSession){
        //      전체 멤버 조회
        List<Members> membersList = memberRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        log.info(String.valueOf(membersList));
        log.info(dto.getId());
//      만약 중복되는 아이디가 있다면
        if(memberRepository.existsById(dto.getId())){
            httpSession.setAttribute("rttrMsg","중복되는 아이디가 있습니다");
            log.info("아이디가 중복됨");
        }
        else {
            if (dto.getPassword().equals(dto.getPassword2())){

                httpSession.setAttribute("rttrMsg","회원가입이 가능합니다");
                log.info("아이디가 중복안됨");
            }
            else{
                httpSession.setAttribute("rttrMsg","비밀번호가 일치하지않습니다");
            }
        }
    }
}