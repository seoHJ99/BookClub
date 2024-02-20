package book.chat.web.controller.member;

import book.chat.domain.service.MemberService;
import book.chat.domain.service.RedisService;
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
    private final RedisService redisService;

    @GetMapping("/join")
    public String joinPage(@ModelAttribute MemberJoinForm memberJoinForm){
        return "layout/member-join";
    }

    /*
    * id 중복 체크.
    * 중복 체크 요청이 들어오면 db에서 해당 id가 존재하는지 확인.
    * 이후 redis에서 다시 체크. 동시에 회원가입 진행중, 같은 id로 중복 체크 통과한 사람이 존재하는지 확인.
    * 만약 redis 에도 없으면 해당 id를 redis에 5분간 저장하고 5분간 유지되는 쿠키를 내려줌.
    * 이후 회원가입 진행시, 쿠키가 존재하면 id중복 체크 통과한걸로 판단하고 계속 진행
    * */
    @PostMapping  // todo ajax 통신으로?? 어떻게 해야하냐?
    public String checkIdDuplicate(@Validated @ModelAttribute MemberJoinForm memberJoinForm,
                                   BindingResult bindingResult,
                                   HttpServletResponse response,
                                   HttpServletRequest request) throws IOException {
        if(bindingResult.hasErrors()){
            return "layout/member-join";
        }
        String id = memberJoinForm.getId();
        MemberDTO memberDTO = memberService.findById(id);

        if(memberDTO != null || redisService.existId(id)){
            response.getWriter().write("<script>alert('이미 존재하는 id입니다.'); </script>");
            return "layout/member-join";
        }
        Cookie cookie = new Cookie(id, UUID.randomUUID().toString());
        cookie.setMaxAge(300); // 5분간 유지되는 쿠키
        response.addCookie(cookie);
        redisService.idDuplicationSave(id);
        request.getSession().setAttribute("idCheck", cookie.getAttribute(id));
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