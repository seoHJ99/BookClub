package book.chat.web.interceptor;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;

@Slf4j
public class LoginCountInterceptor implements HandlerInterceptor {

    final Refill refill = Refill.intervally(5, Duration.ofHours(2));
    final Bandwidth limit = Bandwidth.classic(5, refill);
    private Bucket loginCountBucket = Bucket.builder()
            .addLimit(limit)
            .build();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (request.getMethod().equals("GET")) {
            return true;
        }
        if (request.getMethod().equals("POST") && loginCountBucket.tryConsume(1)) {
            return true;
        }
        response.sendRedirect("/login?redirectURL=" + requestURI);
        return false;
    }
}
