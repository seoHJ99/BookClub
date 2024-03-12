package book.chat.member.dto;

import book.chat.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UpdateForm {
    private String id;
    @NotBlank
    private String pw;
    @NotBlank
    private String pw2;
    @Size(min = 4, max = 15)
    private String nickname;
//    @NotBlank
//    private String profile;
//    @NotBlank
//    private String location;
//    @Email
//    private String mail;
//    private String introduce;
//    @NotBlank
//    private String interest;
}
