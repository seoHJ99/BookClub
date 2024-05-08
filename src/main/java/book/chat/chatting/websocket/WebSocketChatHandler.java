package book.chat.chatting.websocket;


import book.chat.chatting.dto.ChatMessageDto;
import book.chat.chatting.entity.Chatting;
import book.chat.chatting.entity.ChattingRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final ChattingRepository chattingRepository;
    private final ObjectMapper mapper;
    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new ConcurrentHashMap<>();


    /**
     * [소켓 연결 성공시 sessions 에 해당 웹소켓 session 을 저장함. <br/>
     *  현재 연결이 열린 모든 websocket 의 session 은 Set 자료구조 sessions 에 저장됨]
     * @param session (사용자가 연결된 websocket 의 session)
     */

    @Override
    public void afterConnectionEstablished(WebSocketSession session)  {
        sessions.add(session);
        log.info("{} 연결됨", session.getId());
    }

    /**
     * [websocket 을 이용한 메시지 전송 및 로그 db 저장]
     * @param session (메시지가 전송될 websocket 의 session 값)
     * @param message (메시지 본문)
     * */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        HttpSession reqSession = (HttpSession) session.getAttributes().get("reqSession");
        MemberDTO memberDTO = (MemberDTO) reqSession.getAttribute(SessionConst.LOGIN_MEMBER);
        String payload = message.getPayload();
        ChatMessageDto chatMessageDto = mapper.readValue(payload, ChatMessageDto.class);
        chatMessageDto.setMemberProfile(memberDTO.getProfile());
        Long clubNo = chatMessageDto.getClubNo();
        chatMessageDto.setDate(LocalDate.now());
        chatMessageDto.setTime(LocalTime.now());
        chatMessageDto.setMemberId(memberDTO.getId());

        if(!isClubMember(memberDTO, clubNo)){
            log.info("자신의 모임이 아닌 채팅 접근");
            return;
        }

        if (!chatRoomSessionMap.containsKey(clubNo)) {
            chatRoomSessionMap.put(clubNo, new HashSet<>());
        }

        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(clubNo);

        if(chatMessageDto.getMessageType().equals("open")){
            chatRoomSession.add(session);
            sendMessageToChatRoom(chatMessageDto, chatRoomSession);
        }
        if (chatMessageDto.getMessageType().equals("chat")) {
            sendMessageToChatRoom(chatMessageDto, chatRoomSession);
            chattingRepository.save(new Chatting(chatMessageDto));
        }
        if(chatMessageDto.getMessageType().equals("close")){
            sendMessageToChatRoom(chatMessageDto, chatRoomSession);
            chatRoomSession.remove(session);
        }
    }

    /**
     * [해당 맴버가 모임 맴버인지 확인]
     * @param memberDTO (접근하려는 맴버 dto)
     * @param clubNo (클럽 번호)
     * @return boolean (true 면 클럽 맴버. false 면 외부인)
     * */
    private boolean isClubMember(MemberDTO memberDTO, Long clubNo) {
        return memberDTO.getJoinClub().contains(clubNo);
    }

    /**
     * [websocket 연결이 끊김. sessions 에서 해당 웹소켓 session 삭제]
     * */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
    }

    // ====== 채팅 관련 메소드 ======


//    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
//        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
//    }

    /**
     * [Sessions 에서 해당 채팅방의 session을 찾아 sendMessage 메서드 호출]
     * @param chatMessageDto (채팅 메세지 dto)
     * @param chatRoomSession (채팅방의 websocket session)
     * */
    private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDto));//2
    }


    /**
     * [websocket 에 메세지 전송]
     * @param session (메세지를 받을 websocket 세션)
     * @param message (전송될 메세지)
     * */
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}