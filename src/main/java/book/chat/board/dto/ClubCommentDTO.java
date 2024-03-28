package book.chat.board.dto;

import book.chat.board.entity.ClubBoard;
import book.chat.board.entity.ClubBoardComment;
import book.chat.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClubCommentDTO {
    private Long no;
    private Long boardNo;
    private String writerId;
    private String content;
    private LocalDate date;
    private LocalTime time;
    private ClubBoardDTO boardDTO;

    public ClubCommentDTO(ClubBoardComment entity) {
        this.no = entity.getId().getNo();
        this.boardDTO = new ClubBoardDTO(entity.getClubBoard());
        this.boardNo = entity.getId().getBoardNo();
        this.writerId = entity.getId().getWriterId();
        this.content = entity.getContent();
        this.date = entity.getId().getDate();
        this.time = entity.getId().getTime();
    }
}