package book.chat.domain.entity;

import book.chat.web.DTO.ReviewDTO;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Review {

    private Long no;
    private String title;
    private String content;
    private LocalDate writeDate;
    private String writer;
    private String book;

    public Review(ReviewDTO reviewDTO) {
        this.title = reviewDTO.getTitle();
        this.content = reviewDTO.getContent();
        this.writeDate = reviewDTO.getWriteDate();
        this.writer = reviewDTO.getWriter();
        this.book = reviewDTO.getBook();
    }
}
