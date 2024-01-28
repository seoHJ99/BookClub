package book.chat.domain.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {

    private Long no;
    private String title;
    private String content;
    private LocalDate writeDate;
    private String writer;
}
