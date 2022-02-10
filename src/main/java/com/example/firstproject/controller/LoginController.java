package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberDto;
import com.example.firstproject.dto.PassDto;
import com.example.firstproject.entity.Members;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/login")
    public String login(Model model,HttpServletResponse response,HttpSession session) throws Exception{

        String deleteMsg = (String) session.getAttribute("deleteMsg");
        log.info("deleteMSg :  "+deleteMsg);
        if (deleteMsg==null){

        }
        else if (deleteMsg.equals("ok")){
            //UTF-8인코딩 위에서 받아온 매개변수 HttpServletResponse
            response.setContentType("text/html; charset=UTF-8");
            //PrintStream에 있는 모든 출력 메서드 구현돼있는 PrintWriter
            PrintWriter out = response.getWriter();
            //자바스크립트로 알람창 띄움
            out.println("<script>alert(\"회원탈퇴 되었습니다.\")</script>");
            out.flush();
            session.removeAttribute("deleteMsg");
        }

        return "member/login";
    }
    @GetMapping("/new")
    public String newJoin(){
        return "member/newJoin";
    }

    @PostMapping("/login2")
    public String login2(Model model, MemberDto dto, RedirectAttributes rttr, HttpSession session) throws IOException {

        String adminsId="admin@admin.com";
        String adminsPassword="1234";
//        관리자 계정이면
        if (dto.getId().equals(adminsId) && dto.getPassword().equals(adminsPassword)) {
            session.setAttribute("admin",dto.getId());
            session.setAttribute("userId",dto.getId());
            return "member/admins";
        }
        else if(memberRepository.existsById(dto.getId())){
            Members membersEntity = memberRepository.findById(dto.getId()).orElse(null);
            log.info(String.valueOf(membersEntity));
//            알람창추가위한것
            if(membersEntity.getPassword().equals(dto.getPassword())){

                log.info(String.valueOf(dto));
//                로그인 세션 추가
                session.setAttribute("loginMsg","ok");
                session.setAttribute("userId",dto.getId());
                session.setAttribute("userPassword",dto.getPassword());
                return "redirect:/hi";
            }
            else {
                rttr.addFlashAttribute("msg", "비밀번호가 틀렸습니다");
                log.info(String.valueOf(dto));
            }
        }
        else{
            rttr.addFlashAttribute("msg", "아이디가 틀렸습니다");
            log.info(String.valueOf(dto));
        }

        return "redirect:/login";
    }



    @PostMapping("/new2")
    public String createMember(MemberDto dto, RedirectAttributes rttr, PassDto passDto){

        if(passDto.getPassword().equals(passDto.getPassword2())){
//        dto를 Entity로 변환
            Members members =dto.toEntity();
            log.info(String.valueOf(dto));
//        Repository에게 Entity를 저장하게함
            log.info(String.valueOf(members));
            if(!memberRepository.existsById(dto.getId())){

                Members save = memberRepository.save(members);
                log.info(String.valueOf(save));
//          일회성 데이터
                rttr.addFlashAttribute("msg", "회원가입을 축하드립니다");
                return  "redirect:/login";

            }
            else {
                rttr.addFlashAttribute("msg", "이메일계정이 존재합니다");
                return "redirect:/new";
            }

        } else {
            rttr.addFlashAttribute("msg", "비밀번호를 동일하게 입력해주세요");
            return "redirect:/new";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session,RedirectAttributes rttr){

        session.removeAttribute("userId");
        session.removeAttribute("userPassword");
        session.removeAttribute("admin");
        rttr.addFlashAttribute("msg", "로그아웃 되었습니다");

        return "redirect:/login";
    }

    @GetMapping("/hi")
    public String hi(Model model,HttpServletResponse response,HttpSession httpSession) throws IOException {
        log.info((String) model.getAttribute("loginMsg"));
        String str="하이";
        model.addAttribute("hi", str);
        log.info("로그인 세션은: "+(String) httpSession.getAttribute("loginMsg"));
        String loginSession = (String) httpSession.getAttribute("loginMsg");
        log.info("스트링으로 변환한 로그인 세션: "+loginSession);

        if(loginSession==null){
            log.info("세션값이 비어있음");
        }
        else if(loginSession.equals("ok")){
            //UTF-8인코딩 위에서 받아온 매개변수 HttpServletResponse
            response.setContentType("text/html; charset=UTF-8");
            //PrintStream에 있는 모든 출력 메서드 구현돼있는 PrintWriter
            PrintWriter out = response.getWriter();
            out.println("<script>alert(\"로그인 되었습니다.\")</script>");
            out.flush();
            httpSession.removeAttribute("loginMsg");
            log.info(("로그인 세션 삭제후: "+httpSession.getAttribute("loginMsg")));
        }
        return "hi";
    }

//    정보수정 화면으로 이동
    @GetMapping("/members/edit")
    public String membersEdit(HttpSession session){
        return "member/edit";
    }

    //    정보수정 화면으로 이동
    @GetMapping("/toadmin")
    public String membersEdit(){
        return "member/admins";
    }

    @PostMapping("member/edit2")
    public String membersEdit2(HttpSession session,MemberDto dto,RedirectAttributes rttr){

        String id=(String)session.getAttribute("userId");
        log.info(id);
        session.removeAttribute("userPassword");

//        DTO를 엔티티로 변환
        Members members =dto.toEntity();
//        엔티티를 DB로 저장
        Members target = memberRepository.findById(id).orElse(null);    //데이터가 없으면 null
        if(target!=null){
            memberRepository.save(members);
            rttr.addFlashAttribute("msg", "개인정보 수정 완료");
            session.setAttribute("userPassword",dto.getPassword());
        }
        return "redirect:/login";
    }

    @GetMapping("/member/delete/{id}")
    public String delete(@PathVariable String id,HttpSession session){
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
        return "redirect:/login";
    }
    @GetMapping("/go/delete")
    public String goDelete(){
        return "member/delete";
    }

    @GetMapping("/chat")
    public String chatting(){return "chat/chat";}

    @PostMapping("/new/idcheck")
    public String newIdCheck(PassDto dto,RedirectAttributes rttr){
//      전체 멤버 조회
        List<Members> membersList = memberRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        log.info(String.valueOf(membersList));
        log.info(dto.getId());
//      만약 중복되는 아이디가 있다면
        if(memberRepository.existsById(dto.getId())){
            rttr.addFlashAttribute("msg", "중복되는 아이디가 있습니다");
            log.info("아이디가 중복됨");
        }
        else {
            if (dto.getPassword().equals(dto.getPassword2())){
                rttr.addFlashAttribute("msg", "중복되는 아이디가없습니다");
                rttr.addFlashAttribute("idcheck", dto.getId());
                rttr.addFlashAttribute("password", dto.getPassword());
                log.info("아이디가 중복안됨");
            }
            else{
                rttr.addFlashAttribute("msg", "비밀번호가 일치하지않습니다");
            }
        }
        return "redirect:/new";

    }
}
