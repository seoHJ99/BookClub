package book.chat.domain.api.naver;

import book.chat.web.DTO.BookDTO;
import org.assertj.core.api.Assertions;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookSearchAPITest {

    @Test
    void bookSearch() throws IOException, ParseException {
        BookSearchAPI api = new BookSearchAPI();
        List<BookDTO> list = api.bookSearch("움베르토 에코", 10, 1);
        Assertions.assertThat(list.get(0).getAuthor()).isEqualTo("움베르토 에코");
    }

    @Test
    void isbnBookSearch() throws IOException, ParseException {
        BookSearchAPI api = new BookSearchAPI();
        List<BookDTO> list = api.bookSearch("9791192300818");
        Assertions.assertThat(list.get(0).getName()).contains("마흔에 읽는 쇼펜하우어");
    }
}