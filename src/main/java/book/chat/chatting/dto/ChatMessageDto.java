package book.chat.chatting.dto;

import book.chat.chatting.entity.Chatting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private Long no;
    private Long clubNo;
    private String messageType;
    private String memberId;
    private String memberProfile;
    private String message;
    private LocalTime time;
    private LocalDate date;

    public ChatMessageDto(Chatting entity) {
        this.no = entity.getId().getNo();
        this.clubNo = entity.getId().getClubNo();
        this.memberId = entity.getId().getWriter();
        this.message = entity.getContent();
        this.time = entity.getId().getTime();
        this.date = entity.getId().getDate();
    }
}
