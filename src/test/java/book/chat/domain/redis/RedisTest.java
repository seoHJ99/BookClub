package book.chat.domain.redis;

import book.chat.entity.RedisTestEntity;
import book.chat.entity.RedisTestRepository;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
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
        RedisTestEntity entity = new RedisTestEntity(1L, "aaa");

        //when
        RedisTestEntity savedEntity = repository.save(entity);

        //then
        Optional<RedisTestEntity> findEntity = repository.findById(savedEntity.getId());

        assertThat(findEntity.isPresent()).isEqualTo(Boolean.TRUE);
        assertThat(findEntity.get()).isEqualTo(new RedisTestEntity(1L, "aaa"));
    }

    @Test
    public void plusPoint() {
        redisTemplate.opsForZSet().removeRange("test", 0, -1);

        TestRedis dto = new TestRedis("처음");
        TestRedis dto2 = new TestRedis("처음");
        TestRedis dto3 = new TestRedis("두번째");
        TestRedis dto4 = new TestRedis("세번째");
        saveRedisData(dto);
        saveRedisData(dto2);
        saveRedisData(dto3);
        saveRedisData(dto4);

        assertThat(redisTemplate.opsForZSet().reverseRange("test", 0, -1).toString())
                .isEqualTo("[처음, 세번째, 두번째]");
    }

    private void saveRedisData(TestRedis dto) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set popularBooks = zSetOperations.reverseRange("test", 0, -1);
        if (!popularBooks.contains(dto.getName())) {
            redisTemplate.opsForZSet().add("test", dto.getName().toString(), 1);
        } else {
            System.out.println("이미 저장되어 있음");
            redisTemplate.opsForZSet().incrementScore("test", dto.getName().toString(), +1);
        }
    }

    @Test
    public void rightPopTest() throws InterruptedException {
        for(int i =0; i<10; i++){
            redisTemplate.opsForList().rightPush("list", i+"" );
        }
        System.out.println("list="+ redisTemplate.opsForList());
        Object rightPop = redisTemplate.opsForList().rightPop("list", Duration.ofSeconds(1));
        System.out.println("rightPop = " + rightPop);
        Thread.sleep(1);
        System.out.println(redisTemplate.opsForList().rightPop("list", Duration.ofSeconds(1)));

    }



    static class TestRedis {
        private String name;

        public String getName() {
            return name;
        }

        public TestRedis(String name) {
            this.name = name;
        }
    }
}
