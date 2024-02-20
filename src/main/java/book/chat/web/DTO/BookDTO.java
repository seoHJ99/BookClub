package book.chat.web.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDTO {
    private String name;
    private String shoppingLink;
    private String image;
    private String author;
    private String publisher;
    private String isbn;
    private String publishDate;
    private String description;
}
