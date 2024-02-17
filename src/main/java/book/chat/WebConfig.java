package book.chat;

import book.chat.web.interceptor.LogInterceptor;
import book.chat.web.service.CamSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogInterceptor())
//                .order(1)
//                .addPathPatterns("/login");
        // todo LogInterceptor 의 bucket 생성자 주입 안하고도 객체 생성 가능하게 @NoArg... 어노테이션 추가. 문제시 참조할것.
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");
        // todo 화생 채팅 로그는 컨트롤러에 요청이 들어올때 찍고, 나갈때는 어떻게 찍을지 고민.
    }

    @Bean
    public CamSession camSession(){
        return new CamSession();
    }
}
