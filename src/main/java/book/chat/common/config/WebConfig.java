package book.chat.common.config;

import book.chat.common.LogInterceptor;
import book.chat.common.SessionListener;
import book.chat.member.service.MemberService;
import book.chat.login.controller.interceptor.LoginCountInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig  implements WebMvcConfigurer {

//    private final MemberService memberService;

    /**
     * [url 접근시 로그 기록]
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCountInterceptor())
                .order(2)
                .addPathPatterns("/login")
                .excludePathPatterns("/css/**","/*.ico")
                .excludePathPatterns("/v1");

//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(3)
//                .addPathPatterns("/", "/club/**", "/member/**", "/club/**","/meeting/**","/board/**")
//                .excludePathPatterns("/", "css/**", "/*.ico", "/login", "/logout", "/test");
    }

    @Bean
    public SessionListener camSession(){
        return new SessionListener();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
