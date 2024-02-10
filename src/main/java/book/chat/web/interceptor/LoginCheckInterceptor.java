package book.chat.web.interceptor;

import book.chat.web.SessionConst;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final RedisTemplate redisTemplate;
    private final Bucket loginCountBucket;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession();

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            log.info("미인증 사용자 요청");
//            String clientIp = getClientIp(request);
//            int tryCount = countIpLoginTry(clientIp);
//            if(tryCount >=5){
//                response.sendRedirect("/login?redirectURL=" + "login-count-error");
//                return false;
//            }
            if(requestURI.equals("/login") && request.getMethod().equals("POST") && loginCountBucket.tryConsume(1)){
                return true;
            }
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }
        return true;

    }

//    private int countIpLoginTry(String clientIp) {
//        ValueOperations ipCountOperation = redisTemplate.opsForValue();
//        int tryCount=1;
//        if(ipCountOperation.get(clientIp) == null){
//            ipCountOperation.set(clientIp, tryCount);
//        }else {
//            tryCount = (Integer) ipCountOperation.get(clientIp);
//            ipCountOperation.set(clientIp, ++tryCount);
//        }
//        return tryCount;
//    }
//
//    // 클라이언트 ip가져오는 함수
//    public static String getClientIp(HttpServletRequest request) throws UnknownHostException {
//        String ip = request.getHeader("X-Forwarded-For");
//
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("X-Real-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("X-RealIP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("REMOTE_ADDR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//
//        if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1"))
//        {
//            InetAddress address = InetAddress.getLocalHost();
//            ip = address.getHostName() + "/" + address.getHostAddress();
//        }
//        return ip;
//    }
}
