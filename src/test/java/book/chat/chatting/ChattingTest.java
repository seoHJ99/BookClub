package book.chat.chatting;

import book.chat.chatting.dto.ChatMessageDto;
import book.chat.chatting.entity.Chatting;
import book.chat.chatting.entity.ChattingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class ChattingTest {

    @Autowired
    private ChattingRepository chattingRepository;

    @Test
    void chatSave(){
        ChatMessageDto chatMessageDto = new ChatMessageDto(null, 1l, "chat", "test", null, "xxxx", LocalTime.now(), LocalDate.now());
        Chatting chatting = new Chatting(chatMessageDto);
        System.out.println(chatting);
        chattingRepository.save(chatting);
    }

}
