package book.chat.domain.entity;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Club {
    private Long no;
    private String name;
    private String introduce;
    private String location;
    private List<Integer> members;
    private List<LocalDate> meetingDate;
    private List<String> readBooks;
    private List<Long> reportBoard;
}
