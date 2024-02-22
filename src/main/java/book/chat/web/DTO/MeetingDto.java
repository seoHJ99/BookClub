package book.chat.web.DTO;

import book.chat.domain.entity.Meeting;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class MeetingDto {
    @Positive
    private Long clubNo;
    private Long no;
    @NotBlank
    private String meetingName;
    @NotNull
    private String bookTitle;
    @Max(value = 50)
    private int max;
    @NotBlank
    private String joinMember;
//    @NotBlank
    private boolean online;
    @FutureOrPresent
    private LocalDate meetingDate;
    private LocalTime meetingTime;
    @NotBlank
    private String location;

    public MeetingDto(Meeting entity) {
        this.clubNo = entity.getClubNo();
        this.no = entity.getNo();
        this.bookTitle = entity.getBookTitle();
        this.joinMember = entity.getJoinMember();
        this.online = entity.isOnline();
        this.meetingDate = entity.getMeetingDate();
        this.meetingTime = entity.getMeetingTime();
    }
}
