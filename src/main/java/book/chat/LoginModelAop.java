package book.chat;

import book.chat.common.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Log4j2
public class LoginModelAop {

//    @Pointcut("@annotation(org.springframework.stereotype.Controller)")
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void controller(){

    }

    // 모든 패키지 내의 controller package에 존재하는 클래스
    @Around("controller()")
    public Object controllerTest(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object user = session.getAttribute(SessionConst.LOGIN_MEMBER);
        Object[] args = pjp.getArgs();

        for (Object arg : args) {
            if (arg instanceof Model) {
                Model model = (Model) arg;
                model.addAttribute("loginMember", "true");
                if (user == null) {
                    model.addAttribute("loginMember", "false");
                    return pjp.proceed();
                }
            }
        }
        return pjp.proceed();
    }
}