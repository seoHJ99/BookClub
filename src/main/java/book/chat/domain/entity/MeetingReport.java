package book.chat.domain.entity;

import lombok.Getter;

import java.util.Date;

@Getter
public class MeetingReport {
    private Long no;
    private Long clubNo;
    private String title;
    private String content;
    private Date meetingDate;
    private Date writeDate;
    private String book;
}
