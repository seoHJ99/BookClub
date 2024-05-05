package book.chat.board.service;

import book.chat.board.dto.ClubCommentDTO;
import book.chat.board.dto.CommentDTO;
import book.chat.board.entity.ClubCommentRepository;
import book.chat.board.entity.Comment;
import book.chat.board.entity.CommentRepository;
import book.chat.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ClubCommentRepository clubCommentRepository;

    /**
     * [리뷰 댓글 저장]
     * @param commentDTO (저장할 댓글 dto)
     */

    @Transactional
    public CommentDTO save(CommentDTO commentDTO) {
        commentDTO.setDate(LocalDate.now());
        commentDTO.setTime(LocalTime.now());
        Comment saved = commentRepository.save(new Comment(commentDTO));
        return new CommentDTO(saved);
    }

    /**
     * [회원이 작성한 모든 클럽 게시글에 대한 댓글 찾기]
     * @param id (회원 id)
     * @return List (클럽 게시글 댓글 리스트)
     */
    public List<ClubCommentDTO> findClubCommentById(String id) {
        List<ClubCommentDTO> dtoList = clubCommentRepository.findAllByIdWriterIdOrderByIdDateDesc(id).stream()
                .map(ClubCommentDTO::new)
                .collect(Collectors.toList());
        return dtoList;
    }

    /**
     * [리뷰 번호와 댓글 번호로 댓글 찾기]
     * @param boardNo (리뷰 번호)
     * @param commentNo (리뷰에 대한 댓글 번호)
     * @return CommentDTO (댓글 dto)
     * */
    public CommentDTO findByBoardNoAndCommentNo(Long boardNo, Long commentNo){
        Comment entity = commentRepository.findByIdBoardNoAndIdNo(boardNo, commentNo);
        CommentDTO dto = new CommentDTO(entity);
        return dto;
    }

    /**
     * [특정한 리뷰에 달린 모든 댓글 찾기]
     * @param no (리뷰 번호)
     * @return List (댓글 dto 리스트)
     * */
    public List<CommentDTO> findByBoardNo(Long no) {
        List<Comment> entities = commentRepository.findByIdBoardNoOrderByIdDate(no);
        return entities.stream()
                .map(entity -> new CommentDTO(entity))
                .collect(Collectors.toList());
    }
//
//    public List<CommentDTO> findByWriter(String memberId){
//        List<Comment> entity = commentRepository.findByIdWriterId(memberId);
//        return entity.stream().map(CommentDTO::new).collect(Collectors.toList());
//    }

    /**
     * [사용자가 작성한 모든 댓글 찾기]
     * @param memberId (작성자 id)
     * @return List (댓글 dto 리스트)
     * */
    public List<CommentDTO> findByWriter(String memberId) {
        List<Comment> entity = commentRepository.findWithBoardByWriterIdOrderByIdDate(memberId);
        return entity.stream().map(CommentDTO::new).collect(Collectors.toList());
    }

    /**
     * [특정 댓글 삭제]
     * @param commentDTO (삭제할 댓글 dto)
     * @param memberId (요청을 한 사용자 id)
     * @return boolean (삭제되면 true, 안되면 false 반환)
     * */
    public boolean delete(CommentDTO commentDTO, String memberId) {
        if (commentDTO.getWriterId().equals(memberId)) {
            commentRepository.deleteById(commentDTO.getCommentId());
            return true;
        }
        return false;
    }

    /**
     * [특정 리뷰의 모든 댓글 삭제]
     * @param no (리뷰 번호)
     * */
    public void deleteAllBoardComment(Long no){
        commentRepository.deleteAllByIdBoardNo(no);
    }
}
