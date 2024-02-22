package book.chat.web.controller.board;

import book.chat.domain.api.naver.BookSearchAPI;
import book.chat.domain.entity.Comment;
import book.chat.domain.entity.Review;
import book.chat.domain.repository.ReviewBoardRepository;
import book.chat.domain.service.CommentService;
import book.chat.domain.service.ReviewBoardService;
import book.chat.web.DTO.BookDTO;
import book.chat.web.DTO.CommentDTO;
import book.chat.web.DTO.ReviewDTO;
import book.chat.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review")
public class ReviewBoardController {

    private final ReviewBoardService reviewBoardService;
    private final BookSearchAPI bookSearchAPI;
    private final CommentService commentService;

    @GetMapping("/")
    public String reviewBoard(Model model, @RequestParam("no") Long no){
        model.addAttribute("review", reviewBoardService.findReviewByNo(no));
        return "layout/board-main";
    }


    @GetMapping("/list")
    public String allReview(Model model){
        model.addAttribute("boards", reviewBoardService.findAllRecent());
        return "layout/board-list";
    }

    @GetMapping("/save")
    public String writeForm(@ModelAttribute("review") ReviewDTO review){
        return "layout/board-write";
    }

    @PostMapping("/save")
    public String saveReview(@Validated @ModelAttribute("review") ReviewDTO review, BindingResult bindingResult) throws IOException, ParseException {
        if(bindingResult.hasErrors()){
            log.info("error={}", bindingResult);
            return "layout/board-write";
        }
        review.setWriteDate(LocalDateTime.now());
//        review.setWriter((String) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER));
        review.setBook(bookSearchAPI.bookSearch(review.getIsbn()).get(0));
        reviewBoardService.saveReview(review);
        return "redirect: /review?no="+review.getNo();
    }

    @PostMapping("/comment/save")
    public String saveComment(@Validated @ModelAttribute CommentDTO commentDTO,
                              BindingResult bindingResult,
                              Model model){
        commentService.save(commentDTO);
        ReviewDTO review = reviewBoardService.findReviewByNo(commentDTO.getBoardNo());
        model.addAttribute("review", review);
        model.addAttribute("comments", commentService.findByBoardNo(commentDTO.getBoardNo()));
        return "redirect: /review?no="+review.getNo();
    }
}
