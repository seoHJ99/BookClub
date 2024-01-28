package book.chat.domain.DTO;

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
}
