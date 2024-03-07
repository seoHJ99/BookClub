package book.chat.redis.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@Data
@AllArgsConstructor
public class RedisTestEntity {

    @Id
    private Long id;
    private String name;
}