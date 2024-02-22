package book.chat.domain.repository;

import book.chat.domain.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CommentRepository {
    void save(Comment comment);
    List<Comment> findByBoardNo(Long no);
}
