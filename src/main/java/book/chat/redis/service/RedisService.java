package book.chat.redis.service;

import book.chat.board.dto.ReviewDTO;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    /**
     * [서버 시작전 미리 더미 데이터 넣어둠. 인기 책 순위에 사용됨]
     */
    @PostConstruct
    public void saveRankingBooks() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
        redisTemplate.opsForZSet().add("popularBooks", "9788952774224", 1);
        redisTemplate.opsForZSet().add("popularBooks", "9788952774231", 1);
        redisTemplate.opsForZSet().add("popularBooks", "9788952774248", 1);
        redisTemplate.opsForZSet().incrementScore("popularBooks", "9788952774224", 4);
        redisTemplate.opsForZSet().incrementScore("popularBooks", "9788952774231", 3);
        redisTemplate.opsForZSet().incrementScore("popularBooks", "9788952774248", 2);

    }


    /**
     * [중복 체크 통과한 id 값을 id 해쉬코드 값을 키값으로 해서 5분간 redis 저장.<br/>
     * 2명의 사용자가 회원가입 진행시, 둘다 같은 id로 중복 체크 통과할 수도 있기 때문에 사용]
     *
     * @param id (중복 체크 통과한 id)
     * @see RedisService#existId(String id)
     */
    @Transactional
    public void idSave( String id) {
        redisTemplate.opsForValue().set(id.hashCode(), id, Duration.ofMinutes(5));
    }

    /**
     * [uuid 값을 이용해 저장된 중복 통과한 id값을 가져옴]
     * @param key (id 저장 키값)
     * @return 중복 통과한 id값
     * */
    public String getStringValue(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * [redis 에 중복 체크된 id가 저장되어 있는지 확인.]
     *
     * @param id (중복 체크 신청한 id)
     * @return redis 에 같은 값이 있으면 true, 없으면 false (boolean)
     * @see book.chat.member.controller.MemberController#checkIdDuplicate(String, HttpServletResponse, HttpServletRequest)
     */
    public boolean existId(String id) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().get(id.hashCode() + ""));
    }

    /**
     * [해당 책에 대한 리뷰가 작성될 시 인기 점수 +1)
     *
     * @param reviewDTO (리뷰 dto)
     * @see book.chat.board.service.BoardService#saveReview(ReviewDTO)
     */
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


    /**
     * [인기 순위 10위 이내의 책 isbn 리스트 가져오기]
     *
     * @return 인기순위 탑 10 안의 책 isbn 리스트 (List)
     */
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

    /**
     * [매일 자정 redis 인기 책 순위 초기화]
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetBookPopular() {
        redisTemplate.opsForZSet().removeRange("popularBooks", 0, -1);
        log.info("매일 자정 화제의 책 초기화");
    }
}
