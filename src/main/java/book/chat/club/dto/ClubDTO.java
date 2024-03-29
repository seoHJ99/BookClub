package book.chat.club.dto;

import book.chat.club.entity.Club;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClubDTO {
    private Long no;
    private String name;
    private String introduce;
    private String profile;
    private String location;
    private String leader;
    private int range;
    private List<Long> members;
    private List<Long> meetings;
    private List<String> readBooks;
    private List<Long> reportBoard;
    @PastOrPresent
    private LocalDate startDate;

    public ClubDTO(Club entity) {
        this.no = entity.getNo();
        this.name = entity.getName();
        this.profile = entity.getProfile();
        this.introduce = entity.getIntroduce();
        this.location = entity.getLocation();
        this.members = Arrays.stream(entity.getMembers().replaceAll("\\[","")
                        .replaceAll("]","")
                        .split(","))
                .map(element -> element.trim())
                .map(Long::parseLong)
                .collect(Collectors.toList());
        this.meetings = Arrays.stream(entity.getMeetings().replaceAll("\\[","")
                        .replaceAll("]","")
                        .split(","))
                .map(element -> element.trim())
                .map(Long::parseLong)
                .collect(Collectors.toList());
        this.readBooks = Arrays.stream(entity.getReadBooks().replaceAll("\\[","")
                        .replaceAll("]","")
                        .split(","))
                .map(element -> element.trim())
                .collect(Collectors.toList());
        this.reportBoard = Arrays.stream(entity.getReportBoard().replaceAll("\\[","")
                        .replaceAll("]","")
                        .split(","))
                .map(element -> element.trim())
                .map(Long::parseLong)
                .collect(Collectors.toList());
        this.startDate = entity.getStartDate();
    }

    public ClubDTO(ClubMakingForm clubMakingForm) {
        this.name = clubMakingForm.getName();
        this.profile = clubMakingForm.getProfileUrl();
        this.introduce = clubMakingForm.getIntroduce();
        this.location = clubMakingForm.getLocation();
    }
}
