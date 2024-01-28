package book.chat.domain.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeetingDto {
    private Long ClubNo;
    private Long No;
    private String bookTitle;
    private String joinMember;
    private boolean online;
    private LocalDateTime meetingTime;
}
