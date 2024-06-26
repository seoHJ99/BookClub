package book.chat.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Comment.CommentId> {
    Comment save(Comment comment);

    Comment findByIdBoardNoAndIdNo(Long BoardNo, Long no);
    List<Comment> findByIdBoardNoOrderByIdDate(Long no);
    List<Comment> findByIdWriterId(String id);
    @Query("select c from Comment c join fetch c.review where c.id.boardNo = c.review.no and c.id.writerId = :writerId Order by c.id.date")
    List<Comment> findWithBoardByWriterIdOrderByIdDate(@Param("writerId") String writerId);

    void deleteAllByIdBoardNo(Long no);

}
