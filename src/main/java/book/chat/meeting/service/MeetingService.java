package book.chat.meeting.service;

import book.chat.meeting.dto.MeetingDto;
import book.chat.meeting.entity.Meeting;
import book.chat.meeting.entity.MeetingRepository;
import book.chat.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public List<MeetingDto> findRecent10Meetings(){
        return meetingRepository.findTop10ByOrderByMeetingDateDesc().stream()
                .map(MeetingDto::new)
                .collect(Collectors.toList());
    }

    public List<MeetingDto> findRecent10Meetings(Long clubNo){
        return meetingRepository.findAllByIdClubNo(clubNo).stream()
                .map(MeetingDto::new)
                .collect(Collectors.toList());
    }

    public List<MeetingDto> findByClub(Long clubNo) {
        return meetingRepository.findAllByIdClubNo(clubNo).stream()
                .map(meeting -> new MeetingDto(meeting))
                .collect(Collectors.toList());
    }

    public MeetingDto save(MeetingDto meetingDto, MemberDTO meetingMaker) {
        String joinMemberStr = meetingDto.getJoinMember();
        List<Long> joinMember = Arrays.stream(joinMemberStr.split(","))
                .map(Long::parseLong)
                .toList();
        // 본인 추가
        joinMember.add(meetingMaker.getNo());
        meetingDto.setJoinMember(joinMember.toString());
        Meeting savedMeeting = meetingRepository.save(new Meeting(meetingDto));
        return new MeetingDto(savedMeeting);
    }
}