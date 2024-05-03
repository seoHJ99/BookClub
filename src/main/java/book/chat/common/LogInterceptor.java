package book.chat.common;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.util.UUID;

@Slf4j
//@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class LogInterceptor implements HandlerInterceptor {

    // 10초에 10개 토큰. 10초에 10개의 요청만 가능
    final Refill refill = Refill.intervally(10, Duration.ofSeconds(10));

    // 버킷의 토큰 최대 허용치는 10
    final Bandwidth limit = Bandwidth.classic(10, refill);
    private Bucket bucket = Bucket.builder()
            .addLimit(limit)
            .build();

//    public static final String LOGIN_ID = "login_uuid";

    /**
     * [10초에 최대 10개의 요청만 가능. 그 이상의 요청이 오면 429에러 리턴]
     * 
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(bucket.tryConsume(1)){
            return true;
        }
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        return false;
    }
}