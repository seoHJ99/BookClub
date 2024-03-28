package book.chat.chatting.entity;

import book.chat.chatting.dto.ChatMessageDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name = "chatting")
public class Chatting {
    @EmbeddedId
    private ChattingId id;
    private String content;

    @Embeddable
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class ChattingId implements Serializable {
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "no_seq")
        @SequenceGenerator(name = "no_seq", sequenceName = "no_seq", allocationSize = 1)
        @Column(name = "NO")
        private Long no;
        @Column(name = "CLUB_NO")
        private Long clubNo;
        private String writer;
        @Column(name = "CHAT_DATE")
        private LocalDate date;
        @Column(name = "CHAT_TIME")
        private LocalTime time;
    }

    public Chatting(ChatMessageDto dto) {
        this.id = new ChattingId(dto.getNo(), dto.getClubNo(), dto.getMemberId(), dto.getDate(), dto.getTime());
        this.content = dto.getMessage();
    }
}
