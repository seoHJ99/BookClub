package book.chat.board.dto;

import book.chat.board.entity.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class CommentDTO {
    private Long no;
    private Long boardNo;
    private String writerId;
    private String content;
    private LocalDate date;
    private LocalTime time;
    private ReviewDTO reviewDTO;

    public CommentDTO(Comment entity) {
        this.no = entity.getId().getNo();
        this.reviewDTO = new ReviewDTO(entity.getReview());
        this.boardNo = entity.getId().getBoardNo();
        this.writerId = entity.getId().getWriterId();
        this.content = entity.getContent();
        this.date = entity.getId().getDate();
        this.time = entity.getId().getTime();
    }

    public Comment.CommentId getCommentId() {
        return new Comment.CommentId(this.getNo(), this.getBoardNo(), this.writerId, this.getDate(), this.getTime());
    }
}
