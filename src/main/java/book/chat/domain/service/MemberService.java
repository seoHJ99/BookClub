package book.chat.domain.service;

import book.chat.domain.DTO.MemberDTO;
import book.chat.domain.entity.Member;
import book.chat.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final String TEMP_ID = "test";

    public Member updateMemberInfo(MemberDTO newMember){
        // todo 세션에서 id찾기
        String id = TEMP_ID;
        Member entity = memberRepository.findById(id).get();
        return memberRepository.update(new MemberDTO(entity));
    }

    public Optional<Member> findBySession(String sessionKey){
        // todo 세션에서 id찾기
        return memberRepository.findById(TEMP_ID);
    }

    public List<MemberDTO> findByClubNo(Long clubNo){
        return memberRepository.findByClubNo(clubNo).stream()
                .map(member -> new MemberDTO(member))
                .collect(Collectors.toList());
    }
}
