package book.chat.web.controller.board;

import book.chat.domain.service.ClubService;
import book.chat.domain.service.MemberService;
import book.chat.domain.service.RedisService;
import book.chat.domain.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ClubService clubService;
    private final ReviewBoardService reviewBoardService;
    private final RedisService redisService;
    private final MemberService memberService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("clubs",clubService.findRecent4Club());
        model.addAttribute("reviews", reviewBoardService.findRecent10Review());
        model.addAttribute("populars", redisService.getTop10PopularBooks());
        return "layout/home";
    }

}
