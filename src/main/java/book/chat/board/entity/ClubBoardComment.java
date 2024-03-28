package book.chat.board.entity;

import book.chat.board.dto.ClubCommentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Getter
@Entity
@Table(name = "CLUB_COMMENT")
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class ClubBoardComment {
    @EmbeddedId
    private CommentId id;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", insertable = false, updatable = false)
    private ClubBoard clubBoard;


    public ClubBoardComment(ClubCommentDTO commentDTO) {
        this.id = new CommentId(commentDTO.getNo(), commentDTO.getBoardNo(), commentDTO.getWriterId(), commentDTO.getDate(), commentDTO.getTime());
        this.content = commentDTO.getContent();
    }

    @Embeddable
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class CommentId implements Serializable {
        @Column(name = "NO")
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "no_seq")
        @SequenceGenerator(name = "no_seq", sequenceName = "no_seq", allocationSize = 1)
        private Long no;
        @Column(name = "BOARD_NO", insertable = false, updatable = false)
        private Long boardNo;
        @Column(name = "WRITER_ID")
        private String writerId;
        @Column(name = "write_date")
        private LocalDate date;
        @Column(name = "write_time")
        private LocalTime time;
    }
}
