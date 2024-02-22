package book.chat.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.Date;

@Getter
//@Entity
//@Table("meetingReport")
public class MeetingReport {
    private Long no;
    private Long clubNo;
    private String title;
    private String content;
    private Date meetingDate;
    private Date writeDate;
    private String book;
}
