package book.chat.domain.entity;

import book.chat.web.DTO.MeetingDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Entity
@Table(name = "meeting")
public class Meeting {
    @Id
    @Column(name = "club_no")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long clubNo;
    @Column(name = "book_title")
    private String bookTitle;
    @Column(name = "join_member")
    private String joinMember;
    private boolean online;
    @Column(name = "meeting_date")
    private LocalDate meetingDate;
    @Column(name = "meeting_time")
    private LocalTime meetingTime;

    public Meeting(MeetingDto meetingDto) {
        this.clubNo = meetingDto.getClubNo();
        this.bookTitle = meetingDto.getBookTitle();
        this.joinMember = meetingDto.getJoinMember();
        this.online = meetingDto.isOnline();
        this.meetingDate = meetingDto.getMeetingDate();
        this.meetingTime = getMeetingTime();
    }
}
