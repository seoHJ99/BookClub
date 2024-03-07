package book.chat.chatting.websocket;


import book.chat.chatting.dto.ChatMessageDto;
import book.chat.member.dto.MemberDTO;
import book.chat.common.SessionConst;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/*
 * WebSocket Handler 작성
 * 소켓 통신은 서버와 클라이언트가 1:n으로 관계를 맺는다. 따라서 한 서버에 여러 클라이언트 접속 가능
 * 서버에는 여러 클라이언트가 발송한 메세지를 받아 처리해줄 핸들러가 필요
 * TextWebSocketHandler를 상속받아 핸들러 작성
 * 클라이언트로 받은 메세지를 log로 출력하고 클라이언트로 환영 메세지를 보내줌
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;

    private final Set<WebSocketSession> sessions = new HashSet<>();

    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new ConcurrentHashMap<>();


    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결됨", session.getId());

    }

    // 소켓 통신 시 메세지의 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sessions.add(session);
        HttpSession reqSession = (HttpSession) session.getAttributes().get("reqSession");
        MemberDTO memberDTO = (MemberDTO) reqSession.getAttribute(SessionConst.LOGIN_MEMBER);
        String payload = message.getPayload();
        ChatMessageDto chatMessageDto = mapper.readValue(payload, ChatMessageDto.class);
        chatMessageDto.setMemberProfile(memberDTO.getProfile());
        Long chatRoomId = chatMessageDto.getClubNo();
        chatMessageDto.setTime(LocalDateTime.now());
        chatMessageDto.setMemberId(memberDTO.getId());

        if(!isClubMember(memberDTO, chatRoomId)){
            log.info("자신의 모임이 아닌 채팅 접근");
            return;
        }

        if (!chatRoomSessionMap.containsKey(chatRoomId)) {
            chatRoomSessionMap.put(chatRoomId, new HashSet<>());
        }

        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

        if(chatMessageDto.getMessageType().equals("open")){
            chatRoomSession.add(session);
            sendMessageToChatRoom(chatMessageDto, chatRoomSession);
        }
        if (chatMessageDto.getMessageType().equals("chat")) {
            sendMessageToChatRoom(chatMessageDto, chatRoomSession);
        }
        if(chatMessageDto.getMessageType().equals("close")){
            sendMessageToChatRoom(chatMessageDto, chatRoomSession);
            chatRoomSession.remove(session);
        }
    }

    private static boolean isClubMember(MemberDTO memberDTO, Long chatRoomId) {
        return memberDTO.getJoinClub().contains(chatRoomId);
    }

    // 소켓 종료 확인
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
    }

    // ====== 채팅 관련 메소드 ======
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }

    private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDto));//2
    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}