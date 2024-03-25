package book.chat.domain.club;

import book.chat.board.dto.ClubBoardDTO;
import book.chat.board.dto.CommentDTO;
import book.chat.board.service.CommentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class ClubTest {

    @Autowired
    private CommentService service;

    @Test
    void findClubComment(){
        List<CommentDTO> byClubNo = service.findByBoardNo(1l);
        assertThatCode(()->service.findByBoardNo(1l)).doesNotThrowAnyException();
    }

    @Test
    void streamLimitTest() {
        List<Integer> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        list= list.stream()
                .map(entity -> {
                    System.out.println(entity);
                    return 0;
                }).limit(10)
                .collect(Collectors.toList());
        assertThat(list.size()).isEqualTo(10);
    }

    @Test
    void saveList(){
        TestList test = new TestList();
        test.getList().add(5L);
        assertThat(test.getList()).isEqualTo(new ArrayList<>(Arrays.asList(new Long[]{1L,2L,3L,4L,5L})));
    }

    private class TestList{
        private List<Long> list = new ArrayList<>(Arrays.asList(new Long[]{1L,2L,3L,4L}));

        public List<Long> getList() {
            return list;
        }

        public void setList(List<Long> list) {
            this.list = list;
        }
    }
}
