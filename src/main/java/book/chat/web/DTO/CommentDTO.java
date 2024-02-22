package book.chat.web.DTO;

import book.chat.domain.entity.Comment;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long boardNo;
    private String writerId;
    private String content;
    private LocalDate date;
    private LocalDateTime time;

    public CommentDTO(Comment entity) {
        this.boardNo = entity.getBoardNo();
        this.writerId = entity.getWriterId();
        this.content = entity.getContent();
        this.date = entity.getDate();
        this.time = entity.getTime();
    }
}
