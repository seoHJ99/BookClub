package book.chat.board.entity;

import book.chat.board.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Entity
@Table(name = "REVIEW_COMMENT")
@NoArgsConstructor
@ToString
public class Comment {
    @EmbeddedId
    private CommentId id;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", insertable = false, updatable = false)
    private Review review;


    public Comment(CommentDTO commentDTO) {
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
