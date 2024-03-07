package book.chat.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberJoinForm {
    @NotBlank
    private String id;
    @NotBlank
    private String pw;
    @NotBlank
    private String pw2;
    @NotBlank
    @Size(min = 2, max = 5)
    private String name;
    @Size(min = 4, max = 15)
    private String nickname;
    @NotBlank
    private String profile;
    @NotBlank
    private String location;
    @Email
    private String mail;
    private String introduce;
//    @NotBlank
//    private String interest;
}