package book.chat.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class MemberJoinForm {
    @NotBlank
    private String id;
    @NotBlank
    private String pw;
    @NotBlank
    private String pw2;
//    @NotBlank
//    @Size(min = 2, max = 5)
    private String name;
    @Size(min = 4, max = 15)
    private String nickname;
    private MultipartFile profile;
    @NotBlank
    private String location;
    @Email
    private String mail;
    private String introduce;
    private String email;
    private String email2;
    private String profileURL;
//    @NotBlank
//    private String interest;
}
