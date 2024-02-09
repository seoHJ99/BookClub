package book.chat.domain.repository;


import book.chat.domain.entity.Meeting;

import java.time.LocalDate;
import java.util.List;

public interface MeetingRepository {
    List<Meeting> findAll();
    List<Meeting> findAllByDate(LocalDate meetingDate);
    List<Meeting> findAllByClub(long clubNo);
    List<Meeting> findByType(String type);

    Meeting findByNo(long meetingNo);
}
