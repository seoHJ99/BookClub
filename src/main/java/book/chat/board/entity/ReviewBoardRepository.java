package book.chat.board.entity;

import book.chat.board.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewBoardRepository extends JpaRepository<Review, Long> {

    Review save(Review review);
    Review findByNo(Long no);

    List<Review> findAll();
    List<Review> findTop10ByOrderByWriteDateDesc();
    List<Review> findAllByBook(String name);
    List<Review> findByTitleContaining(String title);
    List<Review> findByWriter(String writer);
}
