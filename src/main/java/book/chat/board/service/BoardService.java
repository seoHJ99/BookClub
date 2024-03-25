package book.chat.board.service;

import book.chat.board.dto.ClubBoardDTO;
import book.chat.board.dto.ReviewDTO;
import book.chat.board.entity.*;
import book.chat.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final ReviewBoardRepository reviewBoardRepository;
    private final RedisService redisService;
    private final ClubBoardRepository clubBoardRepository;
    private final ClubCommentRepository clubCommentRepository;

    public ReviewDTO findReviewByNo(Long no) {
        return new ReviewDTO(reviewBoardRepository.findByNo(no));
    }

    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        Review savedReview = reviewBoardRepository.save(new Review(reviewDTO));
        ReviewDTO savedDto = new ReviewDTO(savedReview);
        redisService.bookPopularPlus(savedDto);
        return savedDto;
    }

    @Transactional
    public List<ClubBoardDTO> findClubBoardByClubNo(Long clubNo) {
        List<ClubBoard> collect = clubBoardRepository.findAllLimit10ByClubNoOrderByWriteDateDesc(clubNo);
        return collect.stream()
                .map(ClubBoardDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ClubBoardDTO> findAllClubBoard(Long clubNo) {
        List<ClubBoard> collect = clubBoardRepository.findAllByClubNoOrderByWriteDateDesc(clubNo);
        return collect.stream()
                .map(ClubBoardDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClubBoardDTO findClubBoardByBoardNo(Long no) {

        return new ClubBoardDTO(clubBoardRepository.findByNo(no));
    }

//    public List<ReviewDTO> findByClubNo(Long clubNo){
//        // todo 클럽 번호로 리뷰 찾기 기능. 맵으로 구현은 안해둠.
//        List<Review> reviewEntities = reviewBoardRepository.findBy
//        return entityToDto(reviewEntities);
//    }

    public List<ReviewDTO> findRecent10Review() {
        return entityToDto(reviewBoardRepository.findTop10ByOrderByWriteDateDesc());

    }

    public List<ReviewDTO> findByWriter(String memberId) {
        return entityToDto(reviewBoardRepository.findByWriter(memberId));
    }

    public List<ReviewDTO> entityToDto(List<Review> reviewEntity) {
        return reviewEntity.stream().map(entity -> new ReviewDTO(entity))
                .collect(Collectors.toList());
    }

//    public List<ClubBoardDTO> findByClubNo(Long clubNo){
//        System.out.println("------------------------------------------------");
//        System.out.println("xxxx" +clubBoardRepository.findByNo(clubNo));
//        return clubBoardRepository.findByNo(clubNo).stream()
//                .map(ClubBoardDTO::new)
//                .collect(Collectors.toList());
//    }

}
