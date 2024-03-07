package book.chat.member.service;

import book.chat.member.dto.MemberDTO;
import book.chat.member.dto.MemberJoinForm;
import book.chat.member.entity.Member;
import book.chat.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final String TEMP_ID = "test";

    public void save(MemberJoinForm member){
        memberRepository.save(new Member(member));
    }

    public Member updateMemberInfo(MemberDTO newMember){
        // todo 세션에서 id찾기
        String id = TEMP_ID;
        Member entity = memberRepository.findById(id).get();
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

    public Optional<Member> findBySession(String sessionKey){
        // todo 세션에서 id찾기
        return memberRepository.findById(TEMP_ID);
    }

    public MemberDTO findByNo(Long no){
        return new MemberDTO(memberRepository.findByNo(no));
    }

}
