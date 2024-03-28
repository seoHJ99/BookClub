package book.chat.club.entity;

import book.chat.club.dto.ClubDTO;
import book.chat.club.dto.ClubMakingForm;
import book.chat.member.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "club")
@NoArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "no_seq")
    @SequenceGenerator(name = "no_seq", sequenceName = "no_seq", allocationSize = 1)
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
        this.profile = clubDTO.getProfileUrl();
        this.location = clubDTO.getLocation();
        this.members=memberDTO.getNo().toString();
        this.startDate = LocalDate.now();
        this.meetings = "[0]";
        this.readBooks="[0]";
        this.reportBoard="[0]";
    }
}
