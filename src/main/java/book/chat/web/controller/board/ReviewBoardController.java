package book.chat.web.controller.board;

import book.chat.domain.entity.Review;
import book.chat.domain.repository.ReviewBoardRepository;
import book.chat.domain.service.ReviewBoardService;
import book.chat.web.DTO.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.TimeUnit;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review")
public class ReviewBoardController {

    private final ReviewBoardService reviewBoardService;

    private final StringRedisTemplate redisTemplate;

    @GetMapping("/list")
    public String allReview(Model model){
        model.addAttribute("boards", reviewBoardService.findAllRecent());
        return "layout/board";
    }

    @GetMapping("/save")
    public String writeForm(@ModelAttribute ReviewDTO review){
        return "layout/board-write";
    }

    @PostMapping("/save")
    public String saveReview(@ModelAttribute ReviewDTO reviewDTO){
        // todo 문제 생기면
        if(false){
            return "layout/board";
        }
        reviewBoardService.saveReview(reviewDTO);
        return "layout/review";
    }
}
