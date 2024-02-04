package book.chat.domain.DTO;

import book.chat.domain.entity.Club;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClubDTO {
    private Long no;
    private String name;
    private String introduce;
    private String location;
    private List<Integer> members;
    private List<LocalDate> meetingDate;
    private List<String> readBooks;
    private List<Long> reportBoard;

    public ClubDTO(Club entity) {
        this.no = entity.getNo();
        this.name = entity.getName();
        this.introduce = entity.getIntroduce();
        this.location = entity.getLocation();
        this.members = entity.getMembers();
        this.meetingDate = entity.getMeetingDate();
        this.readBooks = entity.getReadBooks();
        this.reportBoard = entity.getReportBoard();
    }
}
