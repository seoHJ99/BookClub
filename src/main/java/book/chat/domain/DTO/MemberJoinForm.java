package book.chat.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class MemberJoinForm {
    private String id;
    private String pw;
    private String name;
    private String nickName;
    private String location;
    private String mail;
    private String interest;
}
