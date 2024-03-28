package book.chat.board.entity;

import book.chat.board.dto.ClubBoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Entity
@Table(name = "club_board")
@NoArgsConstructor
public class ClubBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "no_seq")
    @SequenceGenerator(name = "no_seq", sequenceName = "no_seq", allocationSize = 1)
    private Long no;
    @Column(name = "club_no")
    private Long clubNo;
    private String title;
    private String content;
    @Column(name = "write_date")
    private LocalDateTime writeDate;
    private String writer;
    private String book;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", insertable = false, updatable = false)
    private List<ClubBoardComment> comment;

    public ClubBoard(ClubBoardDTO reviewDTO) {
        this.title = reviewDTO.getTitle();
        this.content = reviewDTO.getContent();
        this.writeDate = reviewDTO.getWriteDate();
        this.writer = reviewDTO.getWriter();
        this.book= reviewDTO.getIsbn();
        this.comment = reviewDTO.getComment().stream()
                .map(ClubBoardComment::new)
                .collect(Collectors.toList());
    }
}
