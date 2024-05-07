package book.chat.board.controller;

import book.chat.board.dto.ClubBoardDTO;
import book.chat.board.dto.CommentDTO;
import book.chat.board.dto.ReviewDTO;
import book.chat.board.service.BoardService;
import book.chat.board.service.CommentService;
import book.chat.common.ApiMessageConst;
import book.chat.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLSyntaxErrorException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/review")
public class ReviewApiController {

    // todo get 의 경우 경로 파라미터로 모두 변경 필요

    private final BoardService boardService;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;
    private final CommentService commentService;


    /**
     * ["GET /v1/review/:no" 요청 처리 <br/>
     * 해당 번호의 리뷰를 반환.]
     * @param no (글 번호)
     */
    @GetMapping("/{no}")
    public ResponseEntity<String> reviewBoard(@PathVariable(value = "no", required = false) Long no) {
        ReviewDTO review;
        if(no == null){
            return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
        try {
            review = boardService.findReviewByNo(no);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        }

        String data = "";

        try {
            data = objectMapper.writeValueAsString(review);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * ["GET /v1/review/club/:no" 요청 처리<br/>
     * 해당 번호의 클럽 게시글 반환.]
     * @param no (글 번호)
     * */
    @GetMapping("/club/{no}")
    public ResponseEntity<String> clubBoard(@RequestParam(value = "no", required = false) Long no) {
        String data = "";
        ClubBoardDTO review;
        if(no == null){
            return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
        try {
            review = boardService.findClubBoardByBoardNo(no);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        }

        try {
            data = objectMapper.writeValueAsString(review);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * ["POST /v1/review" 요청 처리 <br/>
     * 게시글 저장"]
     * @param reviewDTO (리뷰 dto)
     * @param id (요청자 id)
     * @param pw (요청자 pw)
     * */
    @PostMapping("")
    public ResponseEntity<String> save(ReviewDTO reviewDTO, @RequestParam("id") String id, @RequestParam("pw") String pw) {
        boolean memExist = memberService.checkMember(id, pw);
        if(memExist && reviewDTO.getWriter().equals(id)){
            ReviewDTO reviewDTO1 = boardService.saveReview(reviewDTO);
            String savedData = "";
            try {
                savedData = objectMapper.writeValueAsString(reviewDTO1);
            } catch (JsonProcessingException e) {
                log.info(e.getMessage());
            }
            String data = "{\"redirectURL\" : \"/review?no=\"" + reviewDTO1.getNo() + "\", \"data\" : " + savedData + "}";
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
      return new ResponseEntity<>(ApiMessageConst.NOT_ALLOWED, HttpStatus.FORBIDDEN);
    }

    /**
     * ["DELETE /v1/review/:no" 요청 처리<br/>
     * 해당 번호의 게시글 삭제]
     * @param no (글 번호)
     * @param id (요청자 id)
     * @param pw (요청자 pw)
     * */
    @DeleteMapping("/{no}")
    public ResponseEntity<String> deleteBoard(@PathVariable("no") Long no, @RequestParam String id, @RequestParam String pw) {
        String deleteBoard = null;
        if (memberService.checkMember(id, pw)) {
            ReviewDTO reviewDTO1 = boardService.findReviewByNo(no);
            if (reviewDTO1.getWriter().equals(id)) {
                try {
                    boardService.delete(no, id);
                    deleteBoard = objectMapper.writeValueAsString(reviewDTO1);
                } catch (JsonProcessingException e) {
                    log.info(e.getMessage());
                }
                return new ResponseEntity<>(deleteBoard, HttpStatus.OK);
            }
            return new ResponseEntity<>(ApiMessageConst.NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
    }

    /**
     * ["GET /v1/review/:reviewNo/comment/:commentNo" <br/>
     * 댓글 반환]
     * @param reviewNo (리뷰 번호)
     * @param commentNo (댓글 번호)
     * */
    @GetMapping("/{reviewNo}/comment/{commentNo}")
    public ResponseEntity<String> getComment(@PathVariable("reviewNo") Long reviewNo, @PathVariable("commentNo") Long commentNo){
        ResponseEntity <String> response;
        try{
            CommentDTO commentDTO = commentService.findByBoardNoAndCommentNo(reviewNo, commentNo);
            response = new ResponseEntity<>(objectMapper.writeValueAsString(commentDTO), HttpStatus.OK);
        }catch (NullPointerException e){
            response = new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            response = new ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * ["POST /v1/review/:reviewNo/comment" <br/>
     * 댓글 등록 요청]
     * @param commentDTO (댓글 dto)
     * @param reviewNo (댓글 달 리뷰 번호)
     * @param id (요청자 id)
     * @param pw (요청자 pw)
     * */
    @PostMapping("/{reviewNo}/comment")
    public ResponseEntity<String> saveComment(CommentDTO commentDTO,
                                              @PathVariable("reviewNo") Long reviewNo,
                                              @RequestParam("id") String id,
                                              @RequestParam("pw") String pw){
        ResponseEntity <String> response;
        ReviewDTO reviewByNo = boardService.findReviewByNo(reviewNo);

        if(reviewByNo == null){
            return new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        }
        commentDTO.setReviewDTO(reviewByNo);

        boolean memberExist = memberService.checkMember(id, pw);
        if(!memberExist){
            return new ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
        }

        commentDTO.setWriterId(id);
        try{
            CommentDTO saved = commentService.save(commentDTO);
            response = new ResponseEntity<>(objectMapper.writeValueAsString(saved), HttpStatus.OK);
        }catch (ConstraintViolationException e){
            response = new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            response = new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * ["DELETE /v1/review/:reviewNo/comment/:commentNo"<br/>
     * 댓글 삭제 요청 처리]
     * @param reviewNo (리뷰 번호)
     * @param commentNo (댓글 번호)
     * */
    @DeleteMapping("/{reviewNo}/comment/{commentNo}")
    public ResponseEntity<String> deleteComment(@PathVariable("reviewNo") Long reviewNo,
                                                @PathVariable("commentNo") Long commentNo,
                                                String id, String pw){
        ResponseEntity <String> response;
        boolean memberExist = memberService.checkMember(id, pw);
        if(!memberExist){
            return new ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
        }
        try{
            CommentDTO commentDTO = commentService.findByBoardNoAndCommentNo(reviewNo, commentNo);
            if(!commentDTO.getWriterId().equals(id)){
                return new ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
            }
            commentService.delete(commentDTO, id);
            response = new ResponseEntity<>(objectMapper.writeValueAsString(commentDTO), HttpStatus.OK);
        }catch (ConstraintViolationException e){
            response = new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            response = new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
