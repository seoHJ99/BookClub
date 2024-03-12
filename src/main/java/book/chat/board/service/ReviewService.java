package book.chat.board.service;

import book.chat.board.dto.ReviewDTO;
import book.chat.board.entity.Review;
import book.chat.board.entity.ReviewBoardRepository;
import book.chat.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewBoardRepository reviewBoardRepository;
    private final RedisService redisService;
    private final RedisTemplate redisTemplate;

    public ReviewDTO findReviewByNo(Long no){
        return new ReviewDTO(reviewBoardRepository.findByNo(no));
    }

    public ReviewDTO saveReview(ReviewDTO reviewDTO){
         Review savedReview = reviewBoardRepository.save(new Review(reviewDTO));
         ReviewDTO savedDto = new ReviewDTO(savedReview);
         redisService.bookPopularPlus(savedDto);
         return savedDto;
    }

//    public List<ReviewDTO> findByClubNo(Long clubNo){
//        // todo 클럽 번호로 리뷰 찾기 기능. 맵으로 구현은 안해둠.
//        List<Review> reviewEntities = reviewBoardRepository.findBy
//        return entityToDto(reviewEntities);
//    }

    public List<ReviewDTO> findRecent10Review(){
        return entityToDto( reviewBoardRepository.findTop10ByOrderByWriteDateDesc());

    }

    public List<ReviewDTO> findByWriter(String memberId){
        return entityToDto(reviewBoardRepository.findByWriter(memberId));
    }

    public List<ReviewDTO> entityToDto(List<Review> reviewEntity){
        return reviewEntity.stream().map(entity -> new ReviewDTO(entity))
                .collect(Collectors.toList());
    }
}
