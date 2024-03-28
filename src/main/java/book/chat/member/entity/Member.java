package book.chat.member.entity;

import book.chat.member.dto.MemberDTO;
import book.chat.member.dto.MemberJoinForm;
import book.chat.member.dto.UpdateForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "no_seq")
    @SequenceGenerator(name = "no_seq", sequenceName = "no_seq", allocationSize = 1)
    private Long no;
    private String id;
    private String pw;
    @Column(name = "nickname")
    private String nickname;
    private String location;
    private String introduce;
    private String profile;
    private String mail;
    @Column(name = "review_board")
    private String reviewBoard;
    @Column(name = "join_club")
    private String joinClub;


    public Member(MemberJoinForm joinForm) {
        this.id = joinForm.getId();
        this.pw = joinForm.getPw();
        this.nickname = joinForm.getNickname();
        if (joinForm.getProfileURL().isEmpty()) {
            joinForm.setProfileURL("https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EB%259E%258C&psig=AOvVaw1-0d_0mYJMmvdnoIHghwKr&ust=1708955706825000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDi66DSxoQDFQAAAAAdAAAAABAD");
        }
        this.profile = joinForm.getProfileURL();
        this.location = joinForm.getLocation();
        this.introduce = joinForm.getIntroduce();
        this.mail = joinForm.getMail();
        System.out.println("joinForm.getMail() = " + joinForm.getMail());

    }

    public Member updateField(UpdateForm newMemberDTO) {
        this.profile = newMemberDTO.getProfileUrl();
        this.pw = newMemberDTO.getPw();
        this.nickname = newMemberDTO.getNickname();
        return this;
    }

    public Member updateField(MemberDTO newMemberDTO) {
//        this.profile = newMemberDTO.getProfile();
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
