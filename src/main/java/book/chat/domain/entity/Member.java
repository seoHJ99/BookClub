package book.chat.domain.entity;

import book.chat.web.DTO.MemberDTO;
import book.chat.web.DTO.MemberJoinForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String id;
    private String pw;
    private String nickName;
    private String location;
    private String introduce;
    private String mail;
    @Column(name = "review_board")
    private String reviewBoard;
    @Column(name = "join_club")
    private String joinClub;


    public Member(MemberJoinForm joinForm) {
        this.id = joinForm.getId();
        this.pw = joinForm.getPw();
        this.nickName = joinForm.getNickName();
        this.location = joinForm.getLocation();
        this.introduce = joinForm.getIntroduce();
        this.mail = joinForm.getMail();
    }

    public Member updateField(MemberDTO newMemberDTO){
        this.pw = newMemberDTO.getPw();
        this.mail = newMemberDTO.getMail();
        this.location = newMemberDTO.getLocation();
//        this.interest = newMemberDTO.getInterest();
//        this.nickName = newMemberDTO.getNickName();
        this.joinClub = newMemberDTO.getJoinClub().toString();
        this.reviewBoard = newMemberDTO.getReviewBoard();
        return this;
    }
}
