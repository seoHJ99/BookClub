package book.chat.login.service;

import book.chat.member.entity.Member;
import book.chat.member.entity.MemberRepository;
import book.chat.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public MemberDTO doLogin(String loginId, String password){
        Member entity = memberRepository.findById(loginId)
                .filter(member -> member.getPw().equals(password))
                .orElse(null);
        if(entity== null){
            return null;
        }
        return new MemberDTO(entity);
    }
}
