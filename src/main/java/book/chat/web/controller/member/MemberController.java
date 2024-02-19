package book.chat.web.controller.member;

import book.chat.domain.service.MemberService;
import book.chat.web.DTO.MemberDTO;
import book.chat.web.DTO.MemberJoinForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinPage(@ModelAttribute MemberJoinForm memberJoinForm){
        return "layout/member-join";
    }

    @PostMapping  // todo ajax 통신으로?? 어떻게 해야하냐?
    public String checkIdDuplicate(@Validated @ModelAttribute MemberJoinForm memberJoinForm,
                                   BindingResult bindingResult,
                                   HttpServletResponse response,
                                   HttpServletRequest request) throws IOException {
        if(bindingResult.hasErrors()){
            return "layout/member-join";
        }
        MemberDTO memberDTO = memberService.findById(memberJoinForm.getId());
        if(memberDTO != null){  // redis에서도 검증 코드 추가. 그래야 같은 id생성을 더 확실하게 막음
            response.getWriter().write("<script>alert('이미 존재하는 id입니다.'); </script>");
            return "layout/member-join";
        }
        Cookie cookie = new Cookie(memberJoinForm.getId(), UUID.randomUUID().toString());
        cookie.setMaxAge(300); // 5분간 유지되는 쿠키
        response.addCookie(cookie);
        request.getSession().setAttribute("idCheck", cookie.getAttribute(memberJoinForm.getId()));
        request.getSession().setMaxInactiveInterval(300);
        return "layout/member-join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute MemberJoinForm memberJoinForm,
                       BindingResult bindingResult,
                       @CookieValue(value = "idCheck", required = false) String idCheck,
                       @CookieValue(value = "emailCheck", required = false) String mailCheck,
                       HttpServletRequest request){
        if(!cookieAndSessionSameCheck(idCheck, request)){
            bindingResult.reject("idDoubleCheck", null, null);
            return "layout/member-join";
        }
        // todo 메일도 똑같이.
        if(!pwIsSame(memberJoinForm)){
            bindingResult.reject("pwDoubleCheck", null, null);
            return "layout/member-join";
        }
        if(bindingResult.hasErrors()){
            return "layout/member-join";
        }
        memberService.save(memberJoinForm);
        return "layout/home";
    }

    public boolean cookieAndSessionSameCheck(String idCheck, HttpServletRequest request) {
        return request.getSession(false).getAttribute("idCheck").equals(idCheck);
    }

    public boolean pwIsSame(MemberJoinForm memberJoinForm) {
        return memberJoinForm.getPw().equals(memberJoinForm.getPw2());
    }

    @GetMapping("/{memberId}/info")
    public String memberInfo(@PathVariable("memberInfo") String id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "layout/member-info";
    }
}