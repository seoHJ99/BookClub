package book.chat.web.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class CamSession implements HttpSessionListener {

    /*
    * 캠이 켜질때 session 생성
    * 그리고 이 세션은 직접 커스텀
    * 세션 값은 사용자 id
    * 만약 다른 곳에서 접근하면 다중 로그인으로 판단. 막음.
    * 그냥 나가면 이 세션 자동 삭제.
    * 이 세션이 삭제될때, 나갔다는 로그 찍히도록 커스텀
    * */

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);
        Object memberId = session.getAttribute("memberId");
        log.info("화상 모임 접속 [{}]", memberId);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);
        Object memberId = session.getAttribute("memberId");
        log.info("화상 모임 나감 [{}]", memberId);
    }
}
