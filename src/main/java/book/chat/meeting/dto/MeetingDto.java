package book.chat.meeting.dto;

import book.chat.meeting.entity.Meeting;
import book.chat.member.dto.MemberDTO;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
//    @NotBlank
    private String meetingName;
    @NotNull
    private String name;
    @Max(value = 50)
    private int max;
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

    private List<MemberDTO> meetingMembers = new ArrayList<>();

    public MeetingDto(Meeting entity) {
        this.clubNo = entity.getId().getClubNo();
        this.no = entity.getId().getNo();
        this.name = entity.getBookTitle();
        this.joinMember = Arrays.stream(entity.getJoinMember().replaceAll("\\[", "")
                        .replaceAll("]", "")
                        .split(","))
                .map(String::trim)
                .map((ele) -> {
                    if (!ele.isEmpty()) {
                        return Long.parseLong(ele);
                    }
                    return null;
                })
                .collect(Collectors.toList());
        if (entity.getOnline().equals("Y")) {
            this.online = true;
        } else {
            this.online = false;
        }
//        this.online = entity.isOnline();
        this.max = entity.getMax();
        this.meetingDate = entity.getMeetingDate();
        this.meetingTime = entity.getMeetingTime();
        this.dateTimeAll = LocalDateTime.of(this.meetingDate, this.meetingTime);
        if (meetingDate.isEqual(LocalDate.now())) {
            this.available = true;
        }
    }
}