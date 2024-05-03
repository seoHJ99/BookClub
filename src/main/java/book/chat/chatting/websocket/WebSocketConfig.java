package book.chat.chatting.websocket;

import book.chat.chatting.websocket.interceptor.HttpSessionHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketChatHandler webSocketChatHandler;
    private final HttpSessionHandshakeInterceptor handshakeInterceptor;

    /**
     * [웹소켓 핸들러와 인터셉터 등록]
     * */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // endpoint 설정 : /api/v1/chat/{postId}
        // 이를 통해서 ws://localhost:9090/ws/chat 으로 요청이 들어오면 websocket 통신을 진행한다.
        // setAllowedOrigins("*")는 모든 ip에서 접속 가능하도록 해줌
        registry.addHandler(webSocketChatHandler, "/ws/chat").setAllowedOrigins("*")
                .addInterceptors(handshakeInterceptor);
    }
}