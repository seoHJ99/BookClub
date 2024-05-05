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


    /**
     * [신규 회원 가입]
     * @param member (가입하려는 맴버 정보)
     * @return 가입한 맴버 정보
     * */
    @Transactional
    public MemberDTO save(MemberJoinForm member){
        return new MemberDTO(memberRepository.save(new Member(member)));
    }

    /**
     * [회원 정보 수정]
     * @param newMember (수정된 회원 정보)
     * @param loginMember (로그인한 회원 정보)
     * @return 수정된 회원 정보 (MemberDTO)
     * */
    @Transactional
    public MemberDTO updateMemberInfo(UpdateForm newMember, MemberDTO loginMember){
        Member entity = memberRepository.findById(loginMember.getId()).get();
        String url = awsS3Service.upload(newMember.getProfile());
        newMember.setProfileUrl(url);
        entity.updateField(newMember);
        memberRepository.save(entity);
        return new MemberDTO(entity) ;
    }


    /**
     * [updateForm 대신 memberDTO를 이용한 정보 수정. 클럽 가입시 사용]
     * @param newMember (새로운 맴버 정보)
     * @param loginMember (로그인한 맴버 정보)
     * @return 업데이트된 맴버 정보 엔티티 (Member)
     * @see book.chat.club.service.ClubService#joinMember(MemberDTO, Long)
     * */
    public Member updateMemberInfo(MemberDTO newMember, MemberDTO loginMember){
        Member entity = memberRepository.findById(loginMember.getId()).get();
        entity.updateField(newMember);
        return memberRepository.save(entity);
    }

    /**
     * [id로 맴버 찾기]
     * @param id (맴버 id)
     * @return 찾은 맴버 (MemberDTO)
     * */
    public MemberDTO findById(String id) {
        Optional<Member> entity = memberRepository.findById(id);
        if(entity.isPresent()){
            return new MemberDTO(entity.get());
        }
        return null;
    }

    /**
     * [회원 번호로 찾기]
     * @param no (회원 번호)
     * @return 찾은 회원 (MemberDTO)
     * */
    public MemberDTO findByNo(Long no){
        return new MemberDTO(memberRepository.findByNo(no));
    }

    /**
     * [id 와 pw 값으로 회원인지 확인하기]
     * @param id (회원 id)
     * @param pw (회원 pw)
     * @return 회원이면 true, 아니면 false
     * */

    public boolean checkMember(String id, String pw){
        MemberDTO memberDto;
        try{
            memberDto = findById(id);
            if(memberDto.getPw().equals(pw)){
                return true;
            }
        }catch (NullPointerException e){
            return false;
        }
        return false;
    }
}
