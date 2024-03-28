package book.chat.board.entity;

import book.chat.board.dto.ReviewDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "no_seq")
    @SequenceGenerator(name = "no_seq", sequenceName = "no_seq", allocationSize = 1)
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
        this.book = reviewDTO.getIsbn();
        this.book= reviewDTO.getIsbn();
    }
}
