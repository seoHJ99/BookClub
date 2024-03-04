package book.chat.web.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ChatMessageDto {
    private Long clubNo;
    private String messageType;
    private String memberId;
    private String memberProfile;
    private String message;
    private LocalDateTime time;
}
