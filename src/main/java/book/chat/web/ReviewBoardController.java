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

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
//        redisTemplate.opsForZSet().removeRange("test",0,-1);
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        valueOperations.set("aaaa", "bbbb");

        TestRedis dto = new TestRedis("처음");
        TestRedis dto2 = new TestRedis("처음");
        TestRedis dto3 = new TestRedis("두번째");
        TestRedis dto4 = new TestRedis("세번째");
//        saveRedisData(dto);
//        saveRedisData(dto2);
        saveRedisData(dto3);
        saveRedisData(dto3);
        saveRedisData(dto3);
        saveRedisData(dto4);

        System.out.println(redisTemplate.opsForZSet().rangeWithScores("test", 0, -1));
        return "ok";
    }
    private void saveRedisData(TestRedis dto) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set popularBooks = zSetOperations.reverseRange("test", 0, -1);
        System.out.println("popularBooks = " + popularBooks);
        System.out.println("dto = " + dto.getName());
        System.out.println(popularBooks.contains(dto.getName()));
        if (!popularBooks.contains(dto.getName())) {
            redisTemplate.opsForZSet().add("test", dto.getName().toString(), 1);
        } else {
            System.out.println("여기");
            redisTemplate.opsForZSet().incrementScore("test", dto.getName().toString(), +1);
        }
    }

    static class TestRedis{
        private String name;

        public String getName() {
            return name;
        }

        public TestRedis(String name) {
            this.name = name;
        }
    }
}
