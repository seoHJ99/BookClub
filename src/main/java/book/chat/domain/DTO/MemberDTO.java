package book.chat.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class MemberDTO {
    private Long no;
    private String id;
    private String interest;
    private String meetingInterval;
    private String reviewBoard;
    private List<Long> joinClub;
}
