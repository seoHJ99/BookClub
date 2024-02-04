package book.chat.domain.service;

import book.chat.domain.DTO.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    public void bookPopularPlus(ReviewDTO reviewDTO) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set popularBooks = zSetOperations.range("popularBooks", 0, -1);
        if (!popularBooks.contains(reviewDTO.getBook())) {
            redisTemplate.opsForZSet().add("popularBooks", reviewDTO.getBook(), 1);
        } else {
            redisTemplate.opsForZSet().incrementScore("popularBooks", reviewDTO.getBook(), 1);
        }
    }
}
