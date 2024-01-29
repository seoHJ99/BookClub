package book.chat.domain.api.naver;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BookSearchAPITest {

    @Test
    void bookSearch() throws IOException {
        BookSearchAPI api = new BookSearchAPI();
        api.bookSearch("움베르토 에코");
    }
}