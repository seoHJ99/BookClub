package book.chat.domain.service;

import book.chat.domain.DTO.ReviewDTO;
import book.chat.domain.entity.Review;
import book.chat.domain.repository.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewBoardService {

    private final ReviewBoardRepository reviewBoardRepository;

    public ReviewDTO findReviewByNo(Long no){
        return new ReviewDTO(reviewBoardRepository.findByNo(no));
    }

    public ReviewDTO saveReview(ReviewDTO reviewDTO){
         Review savedReview = reviewBoardRepository.save(new Review(reviewDTO));
         return new ReviewDTO(savedReview);
    }

    public List<ReviewDTO> findByClubNo(Long clubNo){
        // todo 클럽 번호로 리뷰 찾기 기능. 맵으로 구현은 안해둠.
        List<Review> reviewEntities = new ArrayList<>();
        return reviewEntities.stream()
                .map(entity -> new ReviewDTO(entity))
                .collect(Collectors.toList());
    }
}
