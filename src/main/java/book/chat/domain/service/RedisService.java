package book.chat.domain.service;

import book.chat.web.DTO.ReviewDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    @PostConstruct
    public void saveRankingBooks(){
        redisTemplate.getConnectionFactory().getConnection().flushAll();
        redisTemplate.opsForZSet().add("popularBooks", "1111", 1);
        redisTemplate.opsForZSet().add("popularBooks", "2222222", 1);
        redisTemplate.opsForZSet().add("popularBooks", "333333333333", 1);
        redisTemplate.opsForZSet().incrementScore("popularBooks", "1111", 4);
        redisTemplate.opsForZSet().incrementScore("popularBooks", "2222222", 3);
        redisTemplate.opsForZSet().incrementScore("popularBooks", "333333333333", 2);

    }


    // 회원가입시 중복 검사 체크.
    @Transactional
    public void idDuplicationSave(String id) {
        if (!existId(id)) {
            redisTemplate.opsForValue().set(id.hashCode(), id, Duration.ofMinutes(5));
        }
    }

    public boolean existId(String id) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().get(id.hashCode()));
    }

    @Transactional
    public void bookPopularPlus(ReviewDTO reviewDTO) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set popularBooks = zSetOperations.range("popularBooks", 0, -1);
        if (!popularBooks.contains(reviewDTO.getIsbn())) {
            redisTemplate.opsForZSet().add("popularBooks", reviewDTO.getIsbn(), 1);
        } else {
            redisTemplate.opsForZSet().incrementScore("popularBooks", reviewDTO.getIsbn(), 1);
        }
    }

    public List<String> getTop10PopularBooks() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        String popularBooks = zSetOperations.reverseRange("popularBooks", 0, -1).toString();
        List<String> listBooks = Arrays.stream(popularBooks
                        .replaceAll("\\[", "")
                        .replaceAll("]", "")
                        .split(","))
                .toList();
        return listBooks;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetBookPopular() {
        redisTemplate.opsForZSet().removeRange("popularBooks", 0, -1);
        log.info("매일 자정 화제의 책 초기화");
    }
}
