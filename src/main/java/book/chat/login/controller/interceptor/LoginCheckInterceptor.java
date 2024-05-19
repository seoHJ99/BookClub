package book.chat.login.controller.interceptor;


import book.chat.login.controller.LoginController;
import book.chat.member.dto.MemberDTO;
import book.chat.common.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        String redirect = request.getRequestURI();
        Object memberObject = request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        MemberDTO memberDto = (MemberDTO) memberObject;
        log.info("LoginCheckInterceptor.preHandle");
        log.info("member : {}", memberDto);
        // 다른 환경에서 로그인 되었는지 검사
        if (memberDto != null) {
            log.info("로그인 된 사용자");
            if (LoginController.sharedLoginMap.get(memberDto.getId()) != request.getSession().getId()) {
                log.info("다른 사용자가 로그인됨");
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
            log.info("로그인 안한 사용자");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('로그인 후 사용해 주세요'); " +
                    "location.href='/'</script>");
            return false;
        }
        return true;
    }
}
