package book.chat.domain.entity;

import book.chat.web.DTO.ClubDTO;
import book.chat.web.DTO.ClubMakingForm;
import book.chat.web.DTO.MemberDTO;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Club {
    private Long no;
    private String name;
    private String leader;
    private String introduce;
    private String location;
    private List<Integer> members;
    private List<LocalDate> meetings;
    private List<String> readBooks;
    private List<Long> reportBoard;
    // 추가.
    private LocalDate startDate;

    public Club(ClubDTO clubDTO) {
        this.no = clubDTO.getNo();
        this.name = clubDTO.getName();
        this.leader = clubDTO.getLeader();
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
        this.location = clubDTO.getLocation();
        this.leader = memberDTO.getId(); // todo leader 에 member 객체를 넣어도 될듯
    }
}
