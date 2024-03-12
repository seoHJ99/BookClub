package book.chat.board.entity;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Comment.CommentId> {
//    void save(Comment comment);
    // todo orderBy 추가. 나중에 date 와 time 통합한 이후
    List<Comment> findByIdBoardNo(Long no);
    List<Comment> findByIdWriterId(String id);
    @Query("select c from Comment c join fetch c.review where c.id.boardNo = :boardNo and c.id.writerId = :writerId")
    List<Comment> findWithBoardByWriterId(@Param("boardNo") Long boardNo, @Param("writerId") String writerId);

}
