package book.chat.board.controller;

import book.chat.api.naver.BookSearchAPI;
import book.chat.board.service.ReviewService;
import book.chat.board.dto.ReviewDTO;
import book.chat.board.dto.CommentDTO;
import book.chat.board.service.CommentService;
import book.chat.common.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final BookSearchAPI bookSearchAPI;
    private final CommentService commentService;

    @GetMapping("")
    public String reviewBoard(Model model, @RequestParam("no") Long no) {
        ReviewDTO review = reviewService.findReviewByNo(no);
        model.addAttribute("review", review);
        model.addAttribute("comment", new CommentDTO());
        model.addAttribute("book", bookSearchAPI.bookSearch(review.getIsbn()).get(0));
        model.addAttribute("comments", commentService.findByBoardNo(no));
        return "layout/board-main";
    }


    @GetMapping("/list")
    public String allReview(Model model) {
        List<ReviewDTO> rescent10Dtos = reviewService.findRecent10Review();
        List<BookDTO> bookDTOS = rescent10Dtos.stream()
                .map(dto -> bookSearchAPI.bookSearch(dto.getIsbn()).get(0))
                .collect(Collectors.toList());
        model.addAttribute("books", bookDTOS);
        model.addAttribute("boards", rescent10Dtos);
        return "layout/board-list";
    }


    @GetMapping("/save")
    public String writeForm(@ModelAttribute("review") ReviewDTO review) {
        return "layout/board-write";
    }

    @PostMapping("/save")
    public String saveReview(@Validated @ModelAttribute("review") ReviewDTO review, BindingResult bindingResult) throws IOException, ParseException {
        if (bindingResult.hasErrors()) {
            return "layout/board-write";
        }
        review.setWriteDate(LocalDateTime.now());
//        review.setWriter((String) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER));
//        String bookImg = bookSearchAPI.getBookImg(review.getIsbn());
//        review.setBookImg(bookImg);
        review.setIsbn(bookSearchAPI.bookSearch(review.getIsbn()).get(0).getIsbn());
        reviewService.saveReview(review);
        return "redirect: /review?no=" + review.getNo();
    }

    @PostMapping("/comment/save")
    public String saveComment( @Validated @ModelAttribute("comment") CommentDTO comment,
                              BindingResult bindingResult,
                              Model model) {
        commentService.save(comment);
        ReviewDTO review = reviewService.findReviewByNo(comment.getBoardNo());
        return "redirect:/review?no=" + review.getNo();
    }
}
