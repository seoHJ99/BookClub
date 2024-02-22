package book.chat.domain.entity;

import book.chat.web.DTO.CommentDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;
    @Column(name = "writer_id")
    private String writerId;
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "time")
    private Time time;

    public Comment(CommentDTO commentDTO) {
        this.boardNo = commentDTO.getBoardNo();
        this.writerId = commentDTO.getWriterId();
        this.content = commentDTO.getContent();
        this.date = commentDTO.getDate();
        this.time = commentDTO.getTime();
    }
}
