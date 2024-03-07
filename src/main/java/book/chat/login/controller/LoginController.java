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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    public static final Map<String, String> sharedLoginMap = new ConcurrentHashMap<>();

    private final LoginService loginService;


//    @GetMapping("/login")
//    public String loginForm(@ModelAttribute LoginDto loginDto){
//        return "layout/home";
//    }

    @GetMapping("/ss")
    public void ss(HttpServletResponse response) throws IOException {
        response.sendError(400, "aa");
    }

    @PostMapping("/login")
    public String loginProcess(@Validated @ModelAttribute("loginDto") LoginDto loginDto,
                               BindingResult bindingResult,
                               HttpSession session){
        if(bindingResult.hasErrors()){
            return "layout-main/layout";
        }
        MemberDTO loginMember = loginService.doLogin(loginDto.getId(), loginDto.getPw());

        if(loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "layout-main/layout";
        }
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        if(session.getAttribute(loginMember.getId()) != null){
            log.info("이미 로그인한 사용자");
            sharedLoginMap.replace(loginMember.getId(), session.getId());
        }

        sharedLoginMap.put(loginMember.getId(), session.getId());
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
