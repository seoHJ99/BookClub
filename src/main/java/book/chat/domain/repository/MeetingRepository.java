package book.chat.domain.repository;

import book.chat.domain.DTO.MeetingDto;

import java.time.LocalDate;
import java.util.List;

public interface MeetingRepository {
    List<MeetingDto> findAll();
    List<MeetingDto> findAllByDate(LocalDate meetingDate);
    List<MeetingDto> findAllByClub(long clubNo);

    MeetingDto findByNo(long meetingNo);
}
