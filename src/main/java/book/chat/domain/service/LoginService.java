package book.chat.domain.service;

import book.chat.domain.entity.Member;
import book.chat.domain.repository.MemberRepository;
import book.chat.web.DTO.MemberDTO;
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
