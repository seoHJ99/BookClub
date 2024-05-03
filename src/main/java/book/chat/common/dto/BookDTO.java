package book.chat.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
