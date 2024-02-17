package book.chat.web.DTO;

import book.chat.domain.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MemberDTO {
    private Long no;
    private String id;
    private String pw;
    private String nickName;
    private String interest;
    private String meetingInterval;
    private String location;
    private String mail;
    private String reviewBoard;
    private List<Long> joinClub;

    public MemberDTO(Member entity) {
        this.no = entity.getNo();
        this.id = entity.getId();
        this.pw = entity.getPw();
        this.nickName = entity.getNickName();
        this.interest = entity.getInterest();
        this.meetingInterval = entity.getMeetingInterval();
        this.location = entity.getLocation();
        this.mail = entity.getMail();
        this.reviewBoard = entity.getReviewBoard();
        this.joinClub = Arrays.stream(entity.getJoinClub().split(","))
                        .map(string -> Long.parseLong(string))
                        .collect(Collectors.toList());
    }

    public MemberDTO(MemberJoinForm joinForm) {
        this.id = joinForm.getId();
        this.pw = joinForm.getPw();
        this.interest = joinForm.getPw();
        this.nickName = joinForm.getNickName();
        this.location = joinForm.getLocation();
        this.mail = joinForm.getMail();
    }
}
