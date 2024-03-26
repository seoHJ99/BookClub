package book.chat.board.entity;

import book.chat.board.dto.ClubBoardDTO;
import book.chat.board.dto.ClubCommentDTO;
import book.chat.board.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


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
        this.id = new CommentId(commentDTO.getBoardNo(), commentDTO.getWriterId(), LocalDateTime.of(commentDTO.getDate(), commentDTO.getTime()));
        this.content = commentDTO.getContent();
    }

    @Embeddable
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class CommentId implements Serializable {
        @Column(name = "BOARD_NO", insertable = false, updatable = false)
        private Long boardNo;
        @Column(name = "WRITER_ID")
        private String writerId;
        @Column(name = "write_date")
        private LocalDateTime date;
    }
}
