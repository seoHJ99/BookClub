package book.chat.domain.redis;

import book.chat.domain.DTO.ReviewDTO;
import book.chat.domain.service.RedisService;
import book.chat.entity.RedisTestEntity;
import book.chat.entity.RedisTestRepository;
import book.chat.web.ReviewBoardController;
import groovy.util.logging.Slf4j;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest {

    private RedisTestRepository repository;
    private RedisServer redisServer;

    @Autowired
    private RedisTemplate redisTemplate;


    @BeforeEach
    void init() {
        repository = new RedisTestRepository();
    }

    @Test
    public void saveTest() {
        //given
        RedisTestEntity entity = new RedisTestEntity(1L,"aaa");

        //when
        RedisTestEntity savedEntity = repository.save(entity);

        //then
        Optional<RedisTestEntity> findEntity = repository.findById(savedEntity.getId());

        assertThat(findEntity.isPresent()).isEqualTo(Boolean.TRUE);
        assertThat(findEntity.get()).isEqualTo(new RedisTestEntity(1L, "aaa"));
    }

    @Test
    public void plusPoint(){
        redisTemplate.opsForZSet().removeRange("test",0,-1);

        TestRedis dto = new TestRedis("처음");
        TestRedis dto2 = new TestRedis("처음");
        TestRedis dto3 = new TestRedis("두번째");
        TestRedis dto4 = new TestRedis("세번째");
        saveRedisData(dto);
        saveRedisData(dto2);
        saveRedisData(dto3);
        saveRedisData(dto4);

        assertThat(redisTemplate.opsForZSet().reverseRange("test",0,-1).toString())
                .isEqualTo("[처음, 세번째, 두번째]");
    }

    private void saveRedisData(TestRedis dto) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set popularBooks = zSetOperations.reverseRange("test", 0, -1);
        if (!popularBooks.contains(dto.getName())) {
            redisTemplate.opsForZSet().add("test", dto.getName().toString(), 1);
        } else {
            System.out.println("여기");
            redisTemplate.opsForZSet().incrementScore("test", dto.getName().toString(), +1);
        }
        popularBooks = zSetOperations.reverseRange("test", 0, -1);
        System.out.println("popularBooks = " + popularBooks);
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
