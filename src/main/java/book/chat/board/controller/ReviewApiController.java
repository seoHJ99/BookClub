package book.chat.board.controller;

import book.chat.board.dto.ClubBoardDTO;
import book.chat.board.dto.ReviewDTO;
import book.chat.board.service.BoardService;
import book.chat.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/review")
public class ReviewApiController {

    private final BoardService boardService;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;


    /**
     * [/review/v1?no=*** 요청 처리. no가 쿼리값으로 주어져야 함]
     */
    @GetMapping("")
    public ResponseEntity<String> reviewBoard(@RequestParam(value = "no", required = false) Long no) {
        ReviewDTO review;
        try {
            review = boardService.findReviewByNo(no);
        } catch (NullPointerException e) {
            String error = "{\"error\" : \"wrong no\"}";
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
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
            String error = "{\"error\" : \"wrong no\"}";
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
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
        String error = "";
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
            error = "{\"error\" : \"Not exist Allowed\"}";
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
        error = "{\"error\" : \"Not exist member\"}";
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

}
