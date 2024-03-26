package book.chat.meeting.dto;

import book.chat.meeting.entity.Meeting;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@EqualsAndHashCode
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
    private List<Long> joinMember;
//    @NotBlank
    private boolean online;
    @FutureOrPresent
    private LocalDate meetingDate;
    private LocalTime meetingTime;
    private LocalDateTime dateTimeAll;
    private boolean available;
    @NotBlank
    private String location;

    public MeetingDto(Meeting entity) {
        this.clubNo = entity.getId().getClubNo();
        this.no = entity.getId().getNo();
        this.bookTitle = entity.getBookTitle();
        this.joinMember = Arrays.stream(entity.getJoinMember().replaceAll("\\[","")
                        .replaceAll("]","")
                        .split(","))
                .map(element -> element.trim())
                .map(Long::parseLong)
                .collect(Collectors.toList());
        if(entity.getOnline().equals("Y")){
            this.online = true;
        }else {
            this.online = false;
        }
//        this.online = entity.isOnline();
        this.max = entity.getMax();
        this.meetingDate = entity.getMeetingDate();
        this.meetingTime = entity.getMeetingTime();
        this.dateTimeAll = LocalDateTime.of(this.meetingDate, this.meetingTime);
    }
}