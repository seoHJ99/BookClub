package book.chat.domain.service;

import book.chat.domain.entity.Meeting;
import book.chat.domain.repository.MeetingRepository;
import book.chat.web.DTO.MeetingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public List<MeetingDto> findByClub(Long clubNo){
        return meetingRepository.findAllByClub(clubNo).stream()
                .map(meeting -> new MeetingDto(meeting))
                .collect(Collectors.toList());
    }

    public MeetingDto save(MeetingDto meetingDto){
         Meeting savedMeeting = meetingRepository.save(new Meeting(meetingDto));
         return new MeetingDto(savedMeeting);
    }
}
