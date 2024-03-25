package book.chat.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubCommentRepository extends JpaRepository<ClubBoardComment, ClubBoardComment.CommentId> {
}
