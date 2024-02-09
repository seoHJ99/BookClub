package book.chat.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Meeting {
    private Long clubNo;
    private Long no;
    private String bookTitle;
    private String joinMember;
    private boolean online;
    private LocalDateTime meetingTime;
}
