package book.chat.web.DTO;

import book.chat.domain.entity.Meeting;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MeetingDto {
    private Long clubNo;
    private Long no;
    private String bookTitle;
    private String joinMember;
    private boolean online;
    private LocalDateTime meetingTime;

    public MeetingDto(Meeting entity) {
        this.clubNo = entity.getClubNo();
        this.no = entity.getNo();
        this.bookTitle = entity.getBookTitle();
        this.joinMember = entity.getJoinMember();
        this.online = entity.isOnline();
        this.meetingTime = entity.getMeetingTime();
    }
}
