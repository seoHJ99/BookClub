package book.chat.web.DTO;

import book.chat.domain.entity.Meeting;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MeetingDto {
    @NotBlank
    private Long clubNo;
    private Long no;
    @NotNull
    private String bookTitle;
    @Max(value = 50)
    private int max;
    @NotBlank
    private String joinMember;
    @NotBlank
    private boolean online;
    @NotBlank
    @FutureOrPresent
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
