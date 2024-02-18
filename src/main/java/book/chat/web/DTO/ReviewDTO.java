package book.chat.web.DTO;

import book.chat.domain.entity.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReviewDTO {

    private Long no;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private BookDTO book;
    @NotBlank
    @PastOrPresent
    private LocalDate writeDate;
    @NotBlank
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
