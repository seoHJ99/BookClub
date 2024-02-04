package book.chat.domain.DTO;

import book.chat.domain.entity.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReviewDTO {

    private Long no;
    private String title;
    private String content;
    private String book;
    private LocalDate writeDate;
    private String writer;

    public ReviewDTO(Review entity) {
        this.no = entity.getNo();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writeDate = entity.getWriteDate();
        this.writer = entity.getWriter();
        this.book = entity.getBook();
    }
}
