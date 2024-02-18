package book.chat.validation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageTest {

    @Autowired
    MessageSource ms;

    @Test
    void helloMessage() {
        String result = ms.getMessage("NotBlank", null, null);
        assertThat(result).isEqualTo("필수 입력란을 반드시 입력해 주세요.");
    }
}
