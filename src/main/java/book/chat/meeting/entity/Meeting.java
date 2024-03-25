package book.chat.meeting.entity;

import book.chat.meeting.dto.MeetingDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@Table(name = "meeting")
@NoArgsConstructor
@ToString
public class Meeting {
    @EmbeddedId
    private MeetingId id;
    @Column(name = "book_title")
    private String bookTitle;
    @Column(name = "join_member")
    private String joinMember;
    @Column(name = "is_online")
    private String online;
    @Column(name = "meeting_date")
    private LocalDate meetingDate;
    @Column(name = "meeting_time")
    private LocalTime meetingTime;
    @Column(name = "join_max")
    private int max;

    public Meeting(MeetingDto meetingDto) {
        this.id = new MeetingId(meetingDto.getNo(), meetingDto.getClubNo());
        this.bookTitle = meetingDto.getBookTitle();
        this.joinMember = meetingDto.getJoinMember().toString();
        if(meetingDto.isOnline()){
            this.online = "Y";
        }else {
            this.online="N";
        }
        this.meetingDate = meetingDto.getMeetingDate();
        this.meetingTime = meetingDto.getMeetingTime();
        this.max = meetingDto.getMax();
    }

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    public static class MeetingId implements Serializable {
        @Column(name = "NO")
        private Long no;
        @Column(name = "club_no")
        private Long clubNo;
    }
}
