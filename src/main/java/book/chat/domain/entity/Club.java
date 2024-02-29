package book.chat.domain.entity;

import book.chat.web.DTO.ClubDTO;
import book.chat.web.DTO.ClubMakingForm;
import book.chat.web.DTO.MemberDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "club")
@NoArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String name;
//    private String leader;
    private String introduce;
    private String location;
    private String profile;
//    private int range;
    private String members;
    private String meetings;
    @Column(name = "read_books")
    private String readBooks;
    @Column(name = "report_board")
    private String reportBoard;
    @Column(name = "start_date")
    private LocalDate startDate;

    public Club(ClubDTO clubDTO) {
        this.no = clubDTO.getNo();
        this.name = clubDTO.getName();
        this.profile = clubDTO.getProfile();
//        this.leader = clubDTO.getLeader();
//        this.range = clubDTO.getRange();
        this.introduce = clubDTO.getIntroduce();
        this.location = clubDTO.getLocation();
        this.members = clubDTO.getMembers().toString();
        this.meetings = clubDTO.getMeetings().toString();
        this.readBooks = clubDTO.getReadBooks().toString();
        this.reportBoard = clubDTO.getReportBoard().toString();
        this.startDate = clubDTO.getStartDate();
    }

    public Club(ClubMakingForm clubDTO, MemberDTO memberDTO){
        this.name = clubDTO.getName();
        this.introduce = clubDTO.getIntroduce();
//        this.range = clubDTO.getRange();
        this.profile = clubDTO.getProfile();
        this.location = clubDTO.getLocation();
//        this.leader = memberDTO.getId(); // todo leader 에 member 객체를 넣어도 될듯
        this.startDate = LocalDate.now();
    }
}
