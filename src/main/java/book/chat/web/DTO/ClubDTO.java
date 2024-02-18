package book.chat.web.DTO;

import book.chat.domain.entity.Club;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClubDTO {
    private Long no;
    private String name;
    private String introduce;
    private String location;
    private String leader;
    private List<Integer> members;
    private List<LocalDate> meetings;
    private List<String> readBooks;
    private List<Long> reportBoard;
    @PastOrPresent
    private LocalDate startDate;

    public ClubDTO(Club entity) {
        this.no = entity.getNo();
        this.name = entity.getName();
        this.introduce = entity.getIntroduce();
        this.location = entity.getLocation();
        this.members = entity.getMembers();
        this.meetings = entity.getMeetings();
        this.readBooks = entity.getReadBooks();
        this.reportBoard = entity.getReportBoard();
        this.startDate = entity.getStartDate();
    }
}
