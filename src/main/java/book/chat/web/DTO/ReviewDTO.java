package book.chat.web.DTO;

import book.chat.domain.entity.Review;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewDTO {

    private Long no;
    @NotBlank
    private String title;
    @NotBlank
    private String category;
    @NotBlank
    private String content;
    @NotBlank
    private String isbn;
    private LocalDateTime writeDate;
    private String writer;
    private String bookImg;


    public ReviewDTO(Review entity) {
        this.no = entity.getNo();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writeDate = entity.getWriteDate();
        this.writer = entity.getWriter();
        this.isbn = entity.getBook();
//        this.book = entity.getBook();
    }
}
