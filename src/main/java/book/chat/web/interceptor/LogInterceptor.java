package book.chat.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOGIN_ID = "login_uuid";

    // todo 로그인시 uuid 생성은 여기서 하지 않고 로그인 처리 컨트롤러에서 해야됨.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if(request.getRequestURI().equals("/login") && request.getMethod().equals("POST")){
//            String uuid = UUID.randomUUID().toString();
//            request.setAttribute(LOGIN_ID, uuid);
//            log.info("REQUEST [{}][{}][{}]", uuid, request.getSession().getAttribute("id"), handler);
//        }
        return true;  // true여야 실제 handler가 호출됨.
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandler [{}]", modelAndView);
    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        String requestURI = request.getRequestURI();
//        Object logId = request.getAttribute(FACE_CHAT_LOG);
//        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
//        if (ex != null) {
//            log.error("afterCompletion error!!", ex);
//        }
//    }
}