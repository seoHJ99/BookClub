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


    public List<MemberDTO> findMeetingMember(MeetingDto meetingDto) {
        List<Long> joinMember = meetingDto.getJoinMember();
        List<Member> clubMember = memberRepository.findByNoIn(joinMember);
        return clubMember.stream()
                .map(MemberDTO::new)
                .collect(Collectors.toList());
    }

    public MeetingDto findByClubNoAndNo(Long clubNo, Long meetingNo) {
        return new MeetingDto(meetingRepository.findByIdClubNoAndIdNo(clubNo, meetingNo));
    }

    public List<MeetingDto> findRecent10Meetings() {
        return meetingRepository.findTop10ByMeetingDateGreaterThanEqualOrderByMeetingDateDesc(LocalDate.now()).stream()
                .map(MeetingDto::new)
                .peek(meetingDto -> meetingDto.setMeetingMembers(findMeetingMember(meetingDto)))
                .collect(Collectors.toList());
    }

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

    @Transactional
    public MeetingDto save(MeetingDto meetingDto, MemberDTO meetingMaker) {
        meetingDto.getJoinMember().add(meetingMaker.getNo());
        Meeting savedMeeting = meetingRepository.save(new Meeting(meetingDto));
        return new MeetingDto(savedMeeting);
    }

    @Transactional
    public void save(MeetingDto meetingDto) {
        meetingRepository.save(new Meeting(meetingDto));
    }

    @Transactional
    public boolean join(MemberDTO memberDTO, Long clubNo, Long no) {

        MeetingDto meetingDto = findByClubNoAndNo(clubNo, no);
        if (meetingDto.getMax() <= meetingDto.getJoinMember().size()) {
            return false;
        }

        meetingDto.getJoinMember().add(memberDTO.getNo());
        save(meetingDto);
        return true;
    }

    @Transactional
    public void out(MemberDTO memberDTO, Long clubNo, Long no) {
        MeetingDto meetingDto = findByClubNoAndNo(clubNo, no);
        meetingDto.getJoinMember().remove(memberDTO.getNo());
        save(meetingDto);
    }

    public List<MeetingDto> findNotDoneMeeting(Long clubNo) {
        List<Meeting> allNotDoneMeeting = meetingRepository.findAllNotDoneMeeting(clubNo, LocalDate.now());
        return allNotDoneMeeting.stream()
                .map(MeetingDto::new)
                .peek(meetingDto -> meetingDto.setMeetingMembers(findMeetingMember(meetingDto)))
                .collect(Collectors.toList());
    }
}
