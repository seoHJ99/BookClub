package book.chat;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BucketConfiguration {

    @Bean
    public Bucket bucket(){

        // 1분에 10개 토큰
        final Refill refill = Refill.intervally(10, Duration.ofMinutes(1));

        // 버킷의 토큰 최대 허용치는 10
        final Bandwidth limit = Bandwidth.classic(3, refill);

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Bean
    public Bucket loginCountBucket(){
        // 2시간에 5개 토큰
        final Refill refill = Refill.intervally(5, Duration.ofHours(2));

        // 버킷의 토큰 최대 허용치는 10
        final Bandwidth limit = Bandwidth.classic(5, refill);

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
