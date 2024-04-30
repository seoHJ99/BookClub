package book.chat.board.service;

import book.chat.board.dto.ClubBoardDTO;
import book.chat.board.dto.ReviewDTO;
import book.chat.board.entity.*;
import book.chat.common.SessionConst;
import book.chat.member.dto.MemberDTO;
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
    private final CommentService commentService;

    public ReviewDTO findReviewByNo(Long no) {
        return new ReviewDTO(reviewBoardRepository.findByNo(no));
    }

    @Transactional
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
    public ClubBoardDTO findClubBoardByBoardNo(Long no) {

        return new ClubBoardDTO(clubBoardRepository.findByNo(no));
    }


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

    public boolean delete(Long no, MemberDTO memberDTO){
        ReviewDTO reviewDTO = findReviewByNo(no);
        if(reviewDTO.getWriter().equals(memberDTO.getId())){
            reviewBoardRepository.deleteById(no);
            commentService.deleteAllBoardComment(no);
            return true;
        }
        return false;
    }
}
