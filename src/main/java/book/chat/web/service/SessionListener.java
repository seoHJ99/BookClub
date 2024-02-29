package book.chat.web.service;

import book.chat.web.SessionConst;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
public class SessionListener implements HttpSessionListener {


//    public void checkAlreadyLogin(HttpSession httpSession){
//        if(sharedLoginMap.keySet().contains(httpSession.getId())){
//
//        }
//    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Object memberId = session.getAttribute("memberId");
        log.info("유저 로그아웃 [{}]", memberId);
    }
}
