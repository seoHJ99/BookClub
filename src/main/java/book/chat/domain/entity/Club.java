package book.chat.domain.entity;

import book.chat.web.DTO.ClubDTO;
import book.chat.web.DTO.ClubMakingForm;
import book.chat.web.DTO.MemberDTO;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Club {
    private Long no;
    private String name;
    private String leader;
    private String introduce;
    private String location;
    private String profile;
    private int range;
    private List<Integer> members;
    private List<LocalDateTime> meetings;
    private List<String> readBooks;
    private List<Long> reportBoard;
    // 추가.
    private LocalDate startDate;

    public Club(ClubDTO clubDTO) {
        this.no = clubDTO.getNo();
        this.name = clubDTO.getName();
        this.profile = clubDTO.getProfile();
        this.leader = clubDTO.getLeader();
        this.range = clubDTO.getRange();
        this.introduce = clubDTO.getIntroduce();
        this.location = clubDTO.getLocation();
        this.members = clubDTO.getMembers();
        this.meetings = clubDTO.getMeetings();
        this.readBooks = clubDTO.getReadBooks();
        this.reportBoard = clubDTO.getReportBoard();
    }

    public Club(ClubMakingForm clubDTO, MemberDTO memberDTO){
        this.name = clubDTO.getName();
        this.introduce = clubDTO.getIntroduce();
        this.range = clubDTO.getRange();
        this.profile = clubDTO.getProfile();
        this.location = clubDTO.getLocation();
        this.leader = memberDTO.getId(); // todo leader 에 member 객체를 넣어도 될듯
        this.startDate = LocalDate.now();
    }
}
