package book.chat.domain.entity;

import book.chat.domain.DTO.MemberDTO;
import book.chat.domain.DTO.MemberJoinForm;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class Member {
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


    public Member(MemberJoinForm joinForm) {
        this.id = joinForm.getId();
        this.pw = joinForm.getPw();
        this.interest = joinForm.getPw();
        this.nickName = joinForm.getNickName();
        this.location = joinForm.getLocation();
        this.mail = joinForm.getMail();
    }

    public Member updateField(MemberDTO newMemberDTO){
        this.pw = newMemberDTO.getPw();
        this.mail = newMemberDTO.getMail();
        this.location = newMemberDTO.getLocation();
        this.interest = newMemberDTO.getInterest();
//        this.nickName = newMemberDTO.getNickName();
        this.meetingInterval = newMemberDTO.getMeetingInterval();
        return this;
    }
}