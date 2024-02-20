package book.chat.domain.service;

import book.chat.web.DTO.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    @Transactional
    public void idDuplicationSave(String id){
        if(!existId(id)){
            redisTemplate.opsForValue().set(id.hashCode(), id, Duration.ofMinutes(5));
        }
    }

    public boolean existId(String id){
        return Boolean.TRUE.equals(redisTemplate.opsForValue().get(id.hashCode()));
    }

    @Transactional
    public void bookPopularPlus(ReviewDTO reviewDTO) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set popularBooks = zSetOperations.range("popularBooks", 0, -1);
        if (!popularBooks.contains(reviewDTO.getBook())) {
            redisTemplate.opsForZSet().add("popularBooks", reviewDTO.getBook(), 1);
        } else {
            redisTemplate.opsForZSet().incrementScore("popularBooks", reviewDTO.getBook(), 1);
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetBookPopular() {
        redisTemplate.opsForZSet().removeRange("popularBooks",0,-1);
        log.info("매일 자정 화제의 책 초기화");
    }
}
