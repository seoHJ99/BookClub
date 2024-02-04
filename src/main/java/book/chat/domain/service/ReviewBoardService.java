package book.chat.domain.service;

import book.chat.domain.DTO.ReviewDTO;
import book.chat.domain.entity.Review;
import book.chat.domain.repository.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewBoardService {

    private final ReviewBoardRepository reviewBoardRepository;
    private final RedisTemplate redisTemplate;

    public ReviewDTO findReviewByNo(Long no){
        return new ReviewDTO(reviewBoardRepository.findByNo(no));
    }

    public ReviewDTO saveReview(ReviewDTO reviewDTO){
         Review savedReview = reviewBoardRepository.save(new Review(reviewDTO));

         return new ReviewDTO(savedReview);
    }

    
}
