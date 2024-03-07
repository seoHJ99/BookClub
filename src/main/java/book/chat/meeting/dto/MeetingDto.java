package book.chat.meeting.dto;

import book.chat.meeting.entity.Meeting;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class MeetingDto {
    private Long no;
    @Positive
    private Long clubNo;
//    private Long no;
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
        this.clubNo = entity.getId().getClubNo();
        this.no = entity.getId().getNo();
        this.bookTitle = entity.getBookTitle();
        this.joinMember = entity.getJoinMember();
        if(entity.getOnline().equals("Y")){
            this.online = true;
        }else {
            this.online = false;
        }
//        this.online = entity.isOnline();
        this.max = entity.getMax();
        this.meetingDate = entity.getMeetingDate();
        this.meetingTime = entity.getMeetingTime();
    }
}
