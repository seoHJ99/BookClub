package book.chat.web.interceptor;


import book.chat.web.DTO.MemberDTO;
import book.chat.web.SessionConst;
import book.chat.web.controller.member.LoginController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String redirect = request.getRequestURI();
        Object memberObject = request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        MemberDTO memberDto = (MemberDTO) memberObject;
        System.out.println("LoginCheckInterceptor.preHandle");
        System.out.println(memberDto);
        // 다른 환경에서 로그인 되었는지 검사
        if (memberDto != null) {
            System.out.println("로그인 된 사용자");
            if (LoginController.sharedLoginMap.get(memberDto.getId()) != request.getSession().getId()) {
                System.out.println("다른 사용자가 로그인됨");
                log.info("유저 강제 로그아웃 [{}]", memberDto.getId());
                request.getSession().invalidate();
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().write("<script>alert('다른 환경에서 로그인되었습니다.'); " +
                        "location.href='/'</script>");

//                response.sendRedirect("/");
                return false;
            }
        }

//         로그인 한 사용자인지 체크
        if (memberDto == null) {
            System.out.println("로그인 안한 사용자");
            response.sendRedirect("/");
            return false;
        }
        System.out.println("무사 통과");
        return true;
    }
}
