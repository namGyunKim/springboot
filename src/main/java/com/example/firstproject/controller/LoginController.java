package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberDto;
import com.example.firstproject.dto.PassDto;
import com.example.firstproject.entity.Members;
import com.example.firstproject.repository.MemberRepository;
import com.example.firstproject.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String login(Model model,HttpServletResponse response,HttpSession session) throws Exception{

        String deleteMsg = (String) session.getAttribute("deleteMsg");
        log.info("deleteMSg :  "+deleteMsg);
        if (deleteMsg==null){
            log.info("deleteMsg :"+deleteMsg);
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
    public String login2(Model model, MemberDto dto, RedirectAttributes rttr, HttpSession session) {

        String strReturn=memberService.MemberLogin(model,dto, session);
//        rttrMsg세션이 존재한다면
        if(memberService.SessionExist("rttrMsg",session)){
            rttr.addFlashAttribute("msg", session.getAttribute("rttrMsg"));
            session.removeAttribute("rttrMsg");
        }
        return strReturn;
    }



    @PostMapping("/new2")
    public String createMember(MemberDto dto, RedirectAttributes rttr, PassDto passDto){
        String msg=memberService.MemberJoin(dto,passDto);
        rttr.addFlashAttribute("msg", msg);
        return  "redirect:/login";
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
        log.info("로그인 세션은: "+ httpSession.getAttribute("loginMsg"));
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

        memberService.MemberEdit(dto,session);
//       세션이 존재한다면
        if (memberService.SessionExist("rttrMsg",session)){
            rttr.addFlashAttribute("msg", "회원정보가 수정되었습니다");
            session.removeAttribute("rttrMsg");
        }
        return "redirect:/login";
    }

    @GetMapping("/member/delete/{id}")
    public String delete(@PathVariable String id,HttpSession session){
        memberService.MemberDelete(id,session);
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
