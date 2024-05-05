package book.chat.meeting.service;

import book.chat.meeting.dto.MeetingDto;
import book.chat.meeting.entity.Meeting;
import book.chat.meeting.entity.MeetingRepository;
import book.chat.member.dto.MemberDTO;
import book.chat.member.entity.Member;
import book.chat.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;


    /**
     * [일정 참여 맴버 찾기]
     *
     * @param meetingDto (일정 dto)
     * @return 일정에 참여하는 모든 맴버 리스트 (List)
     */
    public List<MemberDTO> findMeetingMember(MeetingDto meetingDto) {
        List<Long> joinMember = meetingDto.getJoinMember();
        List<Member> clubMember = memberRepository.findByNoIn(joinMember);
        return clubMember.stream()
                .map(MemberDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * [일정 찾아오기]
     *
     * @param clubNo    (일정 소유 클럽 번호)
     * @param meetingNo (일정 번호)
     * @return 찾아온 일정 (MeetingDto)
     */
    public MeetingDto findByClubNoAndNo(Long clubNo, Long meetingNo) {
        return new MeetingDto(meetingRepository.findByIdClubNoAndIdNo(clubNo, meetingNo));
    }

    /**
     * [클럽 구분 없이 최근 10개 일정 찾아오기]
     *
     * @return 일정 리스트 (List)
     */
    public List<MeetingDto> findRecent10Meetings() {
        return meetingRepository.findTop10ByMeetingDateGreaterThanEqualOrderByMeetingDateDesc(LocalDate.now()).stream()
                .map(MeetingDto::new)
                .peek(meetingDto -> meetingDto.setMeetingMembers(findMeetingMember(meetingDto)))
                .collect(Collectors.toList());
    }

    /**
     * [특정 클럽의 최근 10개 일정 찾아오기]
     *
     * @param clubNo (클럽 번호)
     * @return 일정 리스트 (List)
     */
    public List<MeetingDto> findRecent10Meetings(Long clubNo) {
        return meetingRepository.findFirst10ByIdClubNoAndMeetingDateGreaterThanEqualOrderByMeetingDateDesc(clubNo, LocalDate.now()).stream()
                .map(MeetingDto::new)
                .peek(meetingDto -> meetingDto.setMeetingMembers(findMeetingMember(meetingDto)))
                .collect(Collectors.toList());
    }

    public List<MeetingDto> findByClub(Long clubNo) {
        return meetingRepository.findFirst10ByIdClubNoAndMeetingDateGreaterThanEqualOrderByMeetingDateDesc(clubNo, LocalDate.now()).stream()
                .map(meeting -> new MeetingDto(meeting))
                .collect(Collectors.toList());
    }


    /**
     * [일정 새로 만들기]
     *
     * @param meetingDto   (일정 dto)
     * @param meetingMaker (일정 만드는 맴버)
     * @return 만들어진 일정 (MeetingDto)
     */
    @Transactional
    public MeetingDto save(MeetingDto meetingDto, MemberDTO meetingMaker) {
        meetingDto.getJoinMember().add(meetingMaker.getNo());
        Meeting savedMeeting = meetingRepository.save(new Meeting(meetingDto));
        return new MeetingDto(savedMeeting);
    }

    /**
     * [미팅 수정하기]
     *
     * @param meetingDto (미팅 저장)
     * @return 수정된 미팅 dto
     */
    @Transactional
    public MeetingDto update(MeetingDto meetingDto) {
        return new MeetingDto(meetingRepository.save(new Meeting(meetingDto)));
    }

    /**
     * [일정 참여하기]
     *
     * @param memberDTO (참여하려는 맴버)
     * @param clubNo    (일정 소유 클럽 번호)
     * @param no        (일정 번호)
     * @return true 이면 참여 성공, false 면 실패. (boolean)
     */
    @Transactional
    public MeetingDto join(MemberDTO memberDTO, Long clubNo, Long no) {

        MeetingDto meetingDto = findByClubNoAndNo(clubNo, no);
        if (meetingDto.getMax() <= meetingDto.getJoinMember().size()) {
            return null;
        }

        meetingDto.getJoinMember().add(memberDTO.getNo());
        return update(meetingDto);
    }

    /**
     * [일정 참여 취소]
     * @param memberDTO (취소 맴버)
     * @param clubNo    (일정 소유 클럽 번호)
     * @param no        (일정 번호)
     * @return 변경된 미팅 dto (MeetingDto)
     */
    @Transactional
    public MeetingDto out(MemberDTO memberDTO, Long clubNo, Long no) {
        MeetingDto meetingDto = findByClubNoAndNo(clubNo, no);
        meetingDto.getJoinMember().remove(memberDTO.getNo());
        return update(meetingDto);
    }

    /**
     * [조회 시간 기준으로 특정 클럽의 참여 가능한 일정 모두 조회]
     *
     * @param clubNo (클럽 번호)
     * @return 조회 시간 기준으로 당일, 미래의 모든 일정 리스트 (List)
     */
    public List<MeetingDto> findNotDoneMeeting(Long clubNo) {
        List<Meeting> allNotDoneMeeting = meetingRepository.findAllNotDoneMeeting(clubNo, LocalDate.now());
        return allNotDoneMeeting.stream()
                .map(MeetingDto::new)
                .peek(meetingDto -> meetingDto.setMeetingMembers(findMeetingMember(meetingDto)))
                .collect(Collectors.toList());
    }
}
