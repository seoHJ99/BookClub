package book.chat.domain.entity;

import book.chat.web.DTO.CommentDTO;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Comment {

    private Long boardNo;
    private String writerId;
    private String content;
    private LocalDate date;
    private LocalDateTime time;

    public Comment(CommentDTO commentDTO) {
        this.boardNo = commentDTO.getBoardNo();
        this.writerId = commentDTO.getWriterId();
        this.content = commentDTO.getContent();
        this.date = commentDTO.getDate();
        this.time = commentDTO.getTime();
    }
}
