package book.chat.board.dto;


import book.chat.board.entity.ClubBoard;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ClubBoardDTO {

    private Long no;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String category;
    @NotBlank
    private String isbn;
    private LocalDateTime writeDate;
    private String writer;
    private String bookImg;
    private List<ClubCommentDTO> comment;

    public ClubBoardDTO(ClubBoard entity) {
        this.no = entity.getNo();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.isbn = entity.getBook();
        this.writeDate = entity.getWriteDate();
        this.writer = entity.getWriter();
//        this.comment = entity.getComment().stream()
//                .map(ClubCommentDTO::new)
//                .collect(Collectors.toList());
    }
}
