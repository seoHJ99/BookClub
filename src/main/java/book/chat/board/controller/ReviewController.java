package book.chat.board.controller;

import book.chat.api.naver.BookSearchAPI;
import book.chat.board.dto.ClubBoardDTO;
import book.chat.board.entity.ClubCommentRepository;
import book.chat.board.service.BoardService;
import book.chat.board.dto.ReviewDTO;
import book.chat.board.dto.CommentDTO;
import book.chat.board.service.CommentService;
import book.chat.common.SessionConst;
import book.chat.common.dto.BookDTO;
import book.chat.member.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class ReviewController {

    private final BoardService boardService;
    private final BookSearchAPI bookSearchAPI;
    private final CommentService commentService;


    @GetMapping("")
    public String reviewBoard(Model model, @RequestParam("no") Long no, HttpSession session) {
        ReviewDTO review = boardService.findReviewByNo(no);
        model.addAttribute("review", review);
        model.addAttribute("comment", new CommentDTO());
        model.addAttribute("book", bookSearchAPI.bookSearch(review.getIsbn()).get(0));
        model.addAttribute("comments", commentService.findByBoardNo(no));
        return "layout/board-main";
    }

    @GetMapping("/club")
    public String clubBoard(Model model, @RequestParam("no") Long no) {
        ClubBoardDTO review = boardService.findClubBoardByBoardNo(no);
        model.addAttribute("review", review);
        model.addAttribute("comment", new CommentDTO());
        try {
            model.addAttribute("book", bookSearchAPI.bookSearch(review.getIsbn()).get(0));
        }catch (IndexOutOfBoundsException e){
            model.addAttribute("book", new BookDTO());
        }

        model.addAttribute("comments", commentService.findByBoardNo(no));
        return "layout/board-main";
    }


    @GetMapping("/list")
    public String allReview(Model model) {
        List<ReviewDTO> rescent10Dtos = boardService.findRecent10Review();
        List<BookDTO> bookDTOS = rescent10Dtos.stream()
                .map(dto -> bookSearchAPI.bookSearch(dto.getIsbn()).get(0))
                .collect(Collectors.toList());
        model.addAttribute("normal", true);
        model.addAttribute("books", bookDTOS);
        model.addAttribute("boards", rescent10Dtos);
        return "layout/board-list";
    }

    @GetMapping("/club/list")
    public String clubBoardList(Model model, @RequestParam("no") Long no, HttpSession session, HttpServletResponse response) throws IOException {
        MemberDTO loginMember =(MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if( !loginMember.getJoinClub().contains(no)){
            response.sendError(HttpStatus.FORBIDDEN.value());
        }
        List<ClubBoardDTO> rescent10Dtos = boardService.findClubBoardByClubNo(no);
        List<BookDTO> bookDTOS;
        try{
            bookDTOS = rescent10Dtos.stream()
                    .map(dto -> bookSearchAPI.bookSearch(dto.getIsbn()).get(0))
                    .collect(Collectors.toList());
        }catch (IndexOutOfBoundsException e){
            bookDTOS = new ArrayList<>();
            for(int i=0; i<rescent10Dtos.size(); i++){
                bookDTOS.add(new BookDTO());
            }
        }

        model.addAttribute("books", bookDTOS);
        model.addAttribute("boards", rescent10Dtos);
        return "layout/board-list";
    }


    @GetMapping("/save")
    public String writeForm(@ModelAttribute("review") ReviewDTO review) {
        return "layout/board-write";
    }

    @PostMapping("/save")
    public String saveReview(@Validated @ModelAttribute("review") ReviewDTO review, BindingResult bindingResult, HttpServletRequest request) throws IOException, ParseException {
        if (bindingResult.hasErrors()) {
            return "layout/board-write";
        }
        review.setWriteDate(LocalDateTime.now());
        MemberDTO member = (MemberDTO) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
        review.setWriter(member.getId() );
        System.out.println(review);
//        String bookImg = bookSearchAPI.getBookImg(review.getIsbn());
//        review.setBookImg(bookImg);
//        review.setIsbn();

        review =boardService.saveReview(review);
        return "redirect:/board?no=" + review.getNo();
    }

    @PostMapping("/comment/save")
    public String saveComment(@Validated @ModelAttribute("comment") CommentDTO comment,
                              BindingResult bindingResult,
                              Model model, HttpSession session) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        comment.setWriterId(loginMember.getId());
        commentService.save(comment);
        ReviewDTO review = boardService.findReviewByNo(comment.getBoardNo());
        return "redirect:/board?no=" + review.getNo();
    }
}
