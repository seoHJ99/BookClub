package book.chat.member.dto;

import book.chat.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UpdateForm {
    @NotBlank
    private String pw;
    @NotBlank
    private String pw2;
    @Size(min = 4, max = 15)
    private String nickname;
    private MultipartFile profile;
    private String profileUrl;
}
