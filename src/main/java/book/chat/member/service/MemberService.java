package book.chat.member.service;

import book.chat.api.aws.AwsS3Service;
import book.chat.meeting.dto.MeetingDto;
import book.chat.meeting.entity.Meeting;
import book.chat.member.dto.MemberDTO;
import book.chat.member.dto.MemberJoinForm;
import book.chat.member.dto.UpdateForm;
import book.chat.member.entity.Member;
import book.chat.member.entity.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AwsS3Service awsS3Service;


    @Transactional
    public MemberDTO save(MemberJoinForm member){
        Member member1 = new Member(member);
        System.out.println(member1);
        return new MemberDTO(memberRepository.save(new Member(member)));
    }

    @Transactional
    public MemberDTO updateMemberInfo(UpdateForm newMember, MemberDTO loginMember){
        Member entity = memberRepository.findById(loginMember.getId()).get();
        String url = awsS3Service.upload(newMember.getProfile());
        newMember.setProfileUrl(url);
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
