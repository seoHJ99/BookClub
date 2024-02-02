package book.chat.domain.DTO;

import book.chat.domain.entity.Member;
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
        this.joinClub = entity.getJoinClub();
    }

    public MemberDTO(MemberJoinForm joinForm) {
        this.id = joinForm.getId();
        this.pw = joinForm.getPw();
        this.interest = joinForm.getPw();
        this.nickName = joinForm.getNickName();
        this.location = joinForm.getLocation();
        this.mail = joinForm.getMail();
    }

    public MemberDTO updateField(MemberDTO newMemberDTO){
        this.pw = newMemberDTO.getPw();
        this.mail = newMemberDTO.getMail();
        this.location = newMemberDTO.getLocation();
        this.interest = newMemberDTO.getInterest();
//        this.nickName = newMemberDTO.getNickName();
        this.meetingInterval = newMemberDTO.getMeetingInterval();
        return this;
    }
}
