package book.chat.web.controller;

import book.chat.domain.DTO.LoginDto;
import book.chat.domain.DTO.MemberDTO;
import book.chat.domain.service.LoginService;
import book.chat.web.SessionConst;
import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class LoginController {

    private LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginDto loginDto){
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@ModelAttribute LoginDto loginDto,
                               BindingResult bindingResult,
                               @RequestParam (defaultValue = "/") String redirectURL,
                               HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "login";
        }
        MemberDTO loginMember = loginService.doLogin(loginDto.getId(), loginDto.getPw());
        if(loginMember == null){
            // todo 로그인 실패 처리
        }
        request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:"+ redirectURL;
    }
}
