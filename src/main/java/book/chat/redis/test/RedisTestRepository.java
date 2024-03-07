package book.chat.redis.test;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RedisTestRepository {

    private Long sequence=0L;
    private static final Map<Long, RedisTestEntity> store = new HashMap<>(); //static

    public RedisTestEntity save(RedisTestEntity entity){
        store.put(++sequence, entity);
        return entity;
    }

    public Optional<RedisTestEntity> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

}
