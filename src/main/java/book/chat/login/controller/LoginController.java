package book.chat.login.controller;

import book.chat.login.dto.LoginDto;
import book.chat.login.service.LoginService;
import book.chat.member.dto.MemberDTO;
import book.chat.common.SessionConst;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    public static final Map<String, String> sharedLoginMap = new ConcurrentHashMap<>();

    private final LoginService loginService;

    @ResponseBody
    @PostMapping("/login")
    public String loginProcess(@Validated @ModelAttribute("loginDto") LoginDto loginDto,
                               BindingResult bindingResult,
                               HttpSession session,
                               HttpServletResponse response) throws IOException {
        if(bindingResult.hasErrors()){
            return "<script>location.href='/';</script>";
        }

        MemberDTO findMember = loginService.doLogin(loginDto.getId(), loginDto.getPw());

        if(findMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "<script>" +
                    "alert('아이디 또는 비밀번호가 맞지 않습니다.');"+
                    "location.href='/';</script>";
        }

        if(session.getAttribute(findMember.getId()) != null){
            log.info("이미 로그인한 사용자");
            sharedLoginMap.replace(findMember.getId(), session.getId());
        }

        session.setAttribute(SessionConst.LOGIN_MEMBER, findMember);
        sharedLoginMap.put(findMember.getId(), session.getId());
        return "<script>location.href='/';</script>";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
