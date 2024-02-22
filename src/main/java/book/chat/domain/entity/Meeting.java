package book.chat.domain.entity;

import book.chat.web.DTO.MeetingDto;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class Meeting {
    private Long clubNo;
    private Long no;
    private String bookTitle;
    private String joinMember;
    private boolean online;
    private LocalDate meetingDate;
    private LocalTime meetingTime;

    public Meeting(MeetingDto meetingDto) {
        this.clubNo = meetingDto.getClubNo();
        this.no = meetingDto.getNo();
        this.bookTitle = meetingDto.getBookTitle();
        this.joinMember = meetingDto.getJoinMember();
        this.online = meetingDto.isOnline();
        this.meetingDate = meetingDto.getMeetingDate();
        this.meetingTime = getMeetingTime();
    }
}
