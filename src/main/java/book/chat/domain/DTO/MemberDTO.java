package book.chat.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
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


    public MemberDTO(MemberJoinForm joinForm) {
        this.id = joinForm.getId();
        this.pw = joinForm.getPw();
        this.interest = joinForm.getPw();
        this.nickName = joinForm.getNickName();
        this.location = joinForm.getLocation();
        this.mail = joinForm.getMail();
    }
}
