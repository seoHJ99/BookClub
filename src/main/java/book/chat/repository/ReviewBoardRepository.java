package book.chat.repository;

import book.chat.DTO.ReviewDTO;

import java.util.List;

public interface ReviewBoardRepository {

    List<ReviewDTO> findAll();
    List<ReviewDTO> findAllByBookName(String name);
    List<ReviewDTO> findAllByClubNo(Long no);
    List<ReviewDTO> findAllByMember(Long no);
    List<ReviewDTO> findAllByMember(String memberName);
    List<ReviewDTO> findAllByReviewName(String name);
}
