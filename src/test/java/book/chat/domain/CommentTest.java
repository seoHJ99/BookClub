package book.chat.domain;

import book.chat.board.entity.Comment;
import book.chat.board.entity.CommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentTest {

    @Autowired
    private CommentRepository commentRepository;


    @Test
    public void fetchJoinTest(){
        List<Comment> test = commentRepository.findWithBoardByWriterId(1l,"test");
        Assertions.assertThat(test.size()).isEqualTo(2);
    }
}
