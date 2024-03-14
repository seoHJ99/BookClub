package book.chat.board.entity;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Comment.CommentId> {
//    void save(Comment comment);
    List<Comment> findByIdBoardNoOrderByIdDate(Long no);
    List<Comment> findByIdWriterId(String id);
    @Query("select c from Comment c join fetch c.review where c.id.boardNo = c.review.no and c.id.writerId = :writerId Order by date")
    List<Comment> findWithBoardByWriterIdOrderByIdDate(@Param("writerId") String writerId);

}
