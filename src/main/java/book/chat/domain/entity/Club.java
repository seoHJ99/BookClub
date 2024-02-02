package book.chat.domain.entity;

import book.chat.domain.DTO.ClubDTO;
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

    public Club(ClubDTO clubDTO) {
        this.no = clubDTO.getNo();
        this.name = clubDTO.getName();
        this.introduce = clubDTO.getIntroduce();
        this.location = clubDTO.getLocation();
        this.members = clubDTO.getMembers();
        this.meetingDate = clubDTO.getMeetingDate();
        this.readBooks = clubDTO.getReadBooks();
        this.reportBoard = clubDTO.getReportBoard();
    }
}
