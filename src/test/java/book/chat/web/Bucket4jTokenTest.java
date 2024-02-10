package book.chat.web;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class Bucket4jTokenTest {

    private Bucket bucket;

    @BeforeEach
    void init(){
        Refill refill = Refill.intervally(2, Duration.ofMinutes(1));
        Bandwidth bandwidth = Bandwidth.classic(4, refill);

        this.bucket = Bucket.builder()
                .addLimit(bandwidth)
                .build();
    }

    @Test
    void bucketTest() throws InterruptedException {
        usingTokenValidate();
        Thread.sleep(10000);
        usingTokenValidate();
    }

    @Test
    void bucketReturnTest(){
        for(int i=0; i<2; i++){
            System.out.println(bucket.tryConsume(10));
            if(bucket.tryConsume(5)){
                System.out.println("aaa");
                return;
            }
        }
        System.out.println("bbbb");
    }

    private void usingTokenValidate() {
        for (int i = 4; i >=0 ; i--) {
            if (bucket.tryConsume(1)) {
                assertThat(bucket.getAvailableTokens()).isEqualTo(i-1);
            } else {
//                System.out.println(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build());
                assertThat(bucket.getAvailableTokens()).isEqualTo(0);
            }
        }
    }
}
