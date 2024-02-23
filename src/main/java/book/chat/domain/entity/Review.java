package book.chat.domain.entity;

import book.chat.web.DTO.BookDTO;
import book.chat.web.DTO.ReviewDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String title;
    private String content;
    @Column(name = "write_date")
    private LocalDateTime writeDate;
    private String writer;
    private String book;

    public Review(ReviewDTO reviewDTO) {
        this.title = reviewDTO.getTitle();
        this.content = reviewDTO.getContent();
        this.writeDate = reviewDTO.getWriteDate();
        this.writer = reviewDTO.getWriter();
        this.book= reviewDTO.getIsbn();
    }
}
