package book.chat.member.dto;

import book.chat.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long no;
    @NotBlank
    private String id;
    @NotBlank
    private String pw;
    @NotBlank
    private String nickname;
    private String profile;
//    private String interest;
    private String location;
    private String mail;
    private String reviewBoard;
    private List<Long> joinClub;
    private String introduce;

    public MemberDTO(Member entity) {
        this.no = entity.getNo();
        this.id = entity.getId();
        this.pw = entity.getPw();
        this.nickname = entity.getNickname();
//        this.interest = entity.getInterest();
        this.location = entity.getLocation();
        this.mail = entity.getMail();
        this.reviewBoard = entity.getReviewBoard();
        if(entity.getJoinClub() == null){
            this.joinClub = new ArrayList<>();
        }else {
            this.joinClub = Arrays.stream(entity.getJoinClub().replaceAll("\\[","")
                            .replaceAll("]","")
                            .replaceAll(" ", "")
                            .split(","))
                    .map(string -> Long.parseLong(string))
                    .collect(Collectors.toList());
        }
        this.profile = entity.getProfile();
        this.introduce = entity.getIntroduce();

    }



    public MemberDTO(MemberJoinForm joinForm) {
        this.id = joinForm.getId();
        this.pw = joinForm.getPw();
//        this.interest = joinForm.getPw();
        this.nickname = joinForm.getNickname();
        this.location = joinForm.getLocation();
        this.mail = joinForm.getMail();
        this.introduce =joinForm.getIntroduce();
    }
}
