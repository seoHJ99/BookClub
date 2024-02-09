package book.chat.domain.repository;

import book.chat.domain.entity.Review;

import java.util.List;

public interface ReviewBoardRepository {

    Review save(Review review);
    Review findByNo(Long no);

    List<Review> findAll();
    List<Review> findAllByBookName(String name);
    List<Review> findAllByClubNo(Long no);
    List<Review> findAllByMember(Long no);
    List<Review> findAllByMember(String memberName);
    List<Review> findAllByReviewName(String name);
}
