package book.chat.domain.api.naver;

import book.chat.api.naver.BookSearchAPI;
import book.chat.common.dto.BookDTO;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookSearchAPITest {
    BookSearchAPI api;

    @BeforeEach
    void init() {
        api = new BookSearchAPI();
    }

    @Test
    void bookSearch() throws IOException, ParseException {
        List<BookDTO> list = api.bookSearch("움베르토 에코", 10, 1);
        assertThat(list.get(0).getAuthor()).isEqualTo("움베르토 에코");
    }

    @Test
    void isbnBookSearch() {
        List<BookDTO> list = api.bookSearch("9791192300818");
        assertThat(list.get(0).getName()).contains("마흔에 읽는 쇼펜하우어");
    }

    @Test
    void findBooksSearchLimitTime() throws InterruptedException {
        for(int i =0; i<1000; i++){
            api.bookSearch("9791192300818");
            System.out.println(i);
            if(i%10==0){
//                Thread.sleep(1000);
            }
        }


//        assertThatThrownBy(() -> {
//            for (int i = 0; i < 100; i++) {
//                api.bookSearch("9791170222743");
//                System.out.println(i);
//            }
//        }).isInstanceOf(Exception.class);
    }
}