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

    private final BoardService boardService;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;
    private final CommentService commentService;


    /**
     * [/review/v1?no=*** 요청 처리. no가 쿼리값으로 주어져야 함]
     */
    @GetMapping("")
    public ResponseEntity<String> reviewBoard(@RequestParam(value = "no", required = false) Long no) {
        ReviewDTO review;
        try {
            review = boardService.findReviewByNo(no);
        } catch (NullPointerException e) {
            return new ResponseEntity(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        }

        String data = "";

        try {
            data = objectMapper.writeValueAsString(review);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/club")
    public ResponseEntity<String> clubBoard(@RequestParam(value = "no", required = false) Long no) {
        String data = "";
        ClubBoardDTO review;
        try {
            review = boardService.findClubBoardByBoardNo(no);
        } catch (NullPointerException e) {
            return new ResponseEntity(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        }

        try {
            data = objectMapper.writeValueAsString(review);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(ReviewDTO reviewDTO) {
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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBoard(@RequestParam("no") Long no, String id, String pw) {
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

    @GetMapping("/comment")
    public ResponseEntity<String> getComment(@RequestParam("clubNo") Long clubNo, @RequestParam("commentNo") Long commentNo){
        ResponseEntity <String> response;
        try{
            CommentDTO commentDTO = commentService.findByBoardNoAndCommentNo(clubNo, commentNo);
            response = new ResponseEntity<>(objectMapper.writeValueAsString(commentDTO), HttpStatus.OK);
        }catch (NullPointerException e){
            response = new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            response = new ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping("/comment/save")
    public ResponseEntity<String> saveComment(CommentDTO commentDTO, String id, String pw){
        ResponseEntity <String> response;
        boolean memberExist = memberService.checkMember(id, pw);
        if(!memberExist){
            return new ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
        }
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

    @DeleteMapping("/comment/delete")
    public ResponseEntity<String> deleteComment(@RequestParam("clubNo") Long clubNo, @RequestParam("commentNo") Long commentNo,
                                                String id, String pw){
        ResponseEntity <String> response;
        boolean memberExist = memberService.checkMember(id, pw);
        if(!memberExist){
            return new ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
        }
        try{
            CommentDTO commentDTO = commentService.findByBoardNoAndCommentNo(clubNo, commentNo);
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
