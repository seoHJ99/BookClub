package book.chat.domain.redis;

import book.chat.entity.RedisTestEntity;
import book.chat.entity.RedisTestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class RedisTest {

    private RedisTestRepository repository;

    @BeforeEach
    void init(){
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
}
