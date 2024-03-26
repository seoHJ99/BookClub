package book.chat.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubCommentRepository extends JpaRepository<ClubBoardComment, ClubBoardComment.CommentId> {

    List<ClubBoardComment> findAllByIdWriterIdOrderByIdDateDesc(String id);
}
