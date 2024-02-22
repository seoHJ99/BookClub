package book.chat.domain.repository;

import book.chat.domain.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository{
    @Override
    public void save(Comment comment) {

    }

    @Override
    public List<Comment> findByBoardNo(Long no) {
        return new ArrayList<Comment>();
    }
}
