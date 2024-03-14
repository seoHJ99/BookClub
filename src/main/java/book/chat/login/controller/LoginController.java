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

    @PostMapping("/login")
    public String loginProcess(@Validated @ModelAttribute("loginDto") LoginDto loginDto,
                               BindingResult bindingResult,
                               HttpSession session,
                               HttpServletResponse response) throws IOException {

        if(bindingResult.hasErrors()){
            return "redirect:/";
        }

        MemberDTO findMeber = loginService.doLogin(loginDto.getId(), loginDto.getPw());

        if(findMeber == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            response.getWriter().write("<script>alert('아이디 또는 비밀번호가 맞지 않습니다.');</script>");
            return "redirect:/";
        }

        if(session.getAttribute(findMeber.getId()) != null){
            log.info("이미 로그인한 사용자");
            sharedLoginMap.replace(findMeber.getId(), session.getId());
        }

        System.out.println(findMeber);

        session.setAttribute(SessionConst.LOGIN_MEMBER, findMeber);
        sharedLoginMap.put(findMeber.getId(), session.getId());
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
