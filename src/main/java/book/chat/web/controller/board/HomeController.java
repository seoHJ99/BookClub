package book.chat.web.controller.board;

import book.chat.domain.api.naver.BookSearchAPI;
import book.chat.domain.service.ClubService;
import book.chat.domain.service.MemberService;
import book.chat.domain.service.RedisService;
import book.chat.domain.service.ReviewBoardService;
import book.chat.web.DTO.BookDTO;
import book.chat.web.DTO.LoginDto;
import book.chat.web.DTO.ReviewDTO;
import jakarta.annotation.PostConstruct;
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
    private final ReviewBoardService reviewBoardService;
    private final RedisService redisService;
    private final MemberService memberService;
    private final BookSearchAPI bookSearchAPI;



    @GetMapping("/")
    public String home(Model model){
        List<ReviewDTO> rescent10Dtos = reviewBoardService.findRecent10Review();
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
