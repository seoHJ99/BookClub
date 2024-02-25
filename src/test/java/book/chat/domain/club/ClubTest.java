package book.chat.domain.club;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ClubTest {

    @Test
    void streamLimitTest() {
        List<Integer> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        list= list.stream()
                .map(entity -> {
                    System.out.println(entity);
                    return 0;
                }).limit(10)
                .collect(Collectors.toList());
        Assertions.assertThat(list.size()).isEqualTo(10);
    }
}
