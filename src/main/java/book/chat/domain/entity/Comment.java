package book.chat.domain.entity;

import book.chat.web.DTO.CommentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@Table(name = "REVIEW_COMMENT")
@NoArgsConstructor
public class Comment {
    @EmbeddedId
    private CommentId id;

    @Column(name = "CONTENT")
    private String content;


    public Comment(CommentDTO commentDTO) {
        this.id = new CommentId(commentDTO.getBoardNo(), commentDTO.getWriterId(), commentDTO.getDate(), commentDTO.getTime());
        this.content = commentDTO.getContent();
    }

    @Embeddable
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class CommentId implements Serializable {
        @Column(name = "BOARD_NO")
        private Long boardNo;
        @Column(name = "WRITER_ID")
        private String writerId;
        @Column(name = "write_date")
        private LocalDate date;
        @Column(name = "write_time")
        private LocalTime time;
    }
}
