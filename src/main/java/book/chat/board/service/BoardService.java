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

    /**
     * [리뷰 번호로 리뷰 찾기]
     * @param  no (리뷰 번호)
     * @return 찾아온 리뷰 (ReviewDTO)
     */
    public ReviewDTO findReviewByNo(Long no) {
        return new ReviewDTO(reviewBoardRepository.findByNo(no));
    }

    /**
     * [리뷰 저장]
     * @param  reviewDTO (저장할 리뷰)
     * @return 저장된 리뷰 리뷰 (ReviewDTO)
     */
    @Transactional
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        Review savedReview = reviewBoardRepository.save(new Review(reviewDTO));
        ReviewDTO savedDto = new ReviewDTO(savedReview);
        redisService.bookPopularPlus(savedDto);
        return savedDto;
    }

    /**
     * [클럽 번호로 모든 클럽 게시글 찾기]
     * @param clubNo (클럽 번호)
     * @return  클럽 게시글 리스트 (List<ClubBoardDTO>)
     */
    @Transactional
    public List<ClubBoardDTO> findClubBoardByClubNo(Long clubNo) {
        List<ClubBoard> collect = clubBoardRepository.findAllLimit10ByClubNoOrderByWriteDateDesc(clubNo);
        return collect.stream()
                .map(ClubBoardDTO::new)
                .collect(Collectors.toList());
    }


    /**
     * [게시글 번호로 클럽 게시글 찾기]
     * @param  no (게시글 번호)
     * @return 찾아온 게시글 (ClubBoardDTO)
     */
    @Transactional
    public ClubBoardDTO findClubBoardByBoardNo(Long no) {
        return new ClubBoardDTO(clubBoardRepository.findByNo(no));
    }


    /**
     * [최근 10개 리뷰 찾기]
     * @return 리뷰 리스트 (List)
     */
    public List<ReviewDTO> findRecent10Review() {
        return entityToDto(reviewBoardRepository.findTop10ByOrderByWriteDateDesc());

    }

    /**
     * [작성자 id로 게시글 찾기]
     * @param memberId (작성자 id)
     * @return 리뷰 리스트 (List)
     */
    public List<ReviewDTO> findByWriter(String memberId) {
        return entityToDto(reviewBoardRepository.findByWriter(memberId));
    }

    /**
     * [Review 리스트를 LReviewDTO 리스트로 전환]
     * @param reviewEntity (리뷰 엔티티)
     * @return 리뷰 DTO 리스트 (List)
     */
    public List<ReviewDTO> entityToDto(List<Review> reviewEntity) {
        return reviewEntity.stream().map(entity -> new ReviewDTO(entity))
                .collect(Collectors.toList());
    }

    /**
     * [리뷰 삭제 및 해당 리뷰의 모든 댓글 삭제]
     * @param no (리뷰 번호)
     * @param memberDTO (리뷰 작성자 DTO)
     * @return true: 정상 삭제.<br/>
     *         false: 삭제 실패
     *         (boolean)
     * @see CommentService#deleteAllBoardComment(Long no)
     */
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
