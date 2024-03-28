package book.chat.board.controller;

import book.chat.api.naver.BookSearchAPI;
import book.chat.board.dto.ReviewDTO;
import book.chat.board.service.BoardService;
import book.chat.club.service.ClubService;
import book.chat.member.service.MemberService;
import book.chat.redis.service.RedisService;
import book.chat.common.dto.BookDTO;
import book.chat.login.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ClubService clubService;
    private final BoardService reviewService;
    private final RedisService redisService;
    private final BookSearchAPI bookSearchAPI;



    @GetMapping("/")
    public String home(Model model){
        List<ReviewDTO> rescent10Dtos = reviewService.findRecent10Review();
        List<BookDTO> bookDTOS = rescent10Dtos.stream()
                .map(dto -> bookSearchAPI.bookSearch(dto.getIsbn()).get(0))
                .collect(Collectors.toList());
        model.addAttribute("loginDto", new LoginDto());
        model.addAttribute("books", bookDTOS);
        model.addAttribute("clubs",clubService.findRecent4Club());
        model.addAttribute("boards", rescent10Dtos);
        model.addAttribute("ranking", redisService.getTop10PopularBooks());
        return "layout/home";
    }
}
