package book.chat.member.service;

import book.chat.member.dto.MemberDTO;
import book.chat.member.dto.MemberJoinForm;
import book.chat.member.dto.UpdateForm;
import book.chat.member.entity.Member;
import book.chat.member.entity.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDTO save(MemberJoinForm member){
        return new MemberDTO(memberRepository.save(new Member(member)));
    }

    public MemberDTO updateMemberInfo(UpdateForm newMember, MemberDTO loginMember){
        Member entity = memberRepository.findById(loginMember.getId()).get();
        entity.updateField(newMember);
        memberRepository.save(entity);
        return new MemberDTO(entity) ;
    }

    public Member updateMemberInfo(MemberDTO newMember, MemberDTO loginMember){
        Member entity = memberRepository.findById(loginMember.getId()).get();
        entity.updateField(newMember);
        return memberRepository.save(entity);
    }

    public MemberDTO findById(String id) {
        Optional<Member> entity = memberRepository.findById(id);
        if(entity.isPresent()){
            return new MemberDTO(entity.get());
        }
        return null;
    }

    public MemberDTO findByNo(Long no){
        return new MemberDTO(memberRepository.findByNo(no));
    }

}
