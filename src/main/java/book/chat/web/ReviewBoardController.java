package book.chat.web;

import book.chat.domain.repository.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;
import java.util.concurrent.TimeUnit;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewBoardController {

    private final ReviewBoardRepository reviewBoardRepository;

    private final StringRedisTemplate redisTemplate;

    @RequestMapping("/reviews")
    public String seeBoard(Model model){
        log.info("board all= {}", reviewBoardRepository.findAll());
        model.addAttribute("boards", reviewBoardRepository.findAll());
        return "/board";
    }
}
