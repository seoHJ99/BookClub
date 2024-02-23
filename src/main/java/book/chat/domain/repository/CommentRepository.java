package book.chat.domain.repository;

import book.chat.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Comment.CommentId> {
//    void save(Comment comment);
    // todo orderBy 추가. 나중에 date 와 time 통합한 이후
    List<Comment> findByIdBoardNo(Long no);
}
