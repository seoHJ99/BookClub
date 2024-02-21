package book.chat;

import book.chat.domain.service.MemberService;
import book.chat.web.interceptor.LogInterceptor;
import book.chat.web.interceptor.LoginCheckInterceptor;
import book.chat.web.service.CamSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig  implements WebMvcConfigurer {

    private final MemberService memberService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/login")
                .excludePathPatterns("/css/**","/*.ico");
    }

    @Bean
    public CamSession camSession(){
        return new CamSession();
    }
}
