package book.chat.web.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank
    private String id;
    @NotBlank
    private String pw;
}
