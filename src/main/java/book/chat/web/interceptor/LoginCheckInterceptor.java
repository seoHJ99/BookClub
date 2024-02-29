package book.chat.web.interceptor;


import book.chat.web.DTO.MemberDTO;
import book.chat.web.SessionConst;
import book.chat.web.controller.member.LoginController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;



public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MemberDTO memberDto = (MemberDTO) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
        if( memberDto != null){
            System.out.println("로그인된 사용자");
            if(LoginController.sharedLoginMap.get(memberDto.getId()) != request.getSession().getId()){
                System.out.println("다른 사용자가 로그인됨");
                request.getSession().invalidate();
                return false;
            }
        }
        return true;
    }
}
