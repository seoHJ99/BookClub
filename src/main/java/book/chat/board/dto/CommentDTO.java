package book.chat.board.dto;

import book.chat.board.entity.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class CommentDTO {
    private Long boardNo;
    private String writerId;
    private String content;
    private LocalDate date;
    private LocalTime time;

    public CommentDTO(Comment entity) {
        this.boardNo = entity.getId().getBoardNo();
        this.writerId = entity.getId().getWriterId();
        this.content = entity.getContent();
        this.date = entity.getId().getDate();
        this.time = entity.getId().getTime();
    }
}