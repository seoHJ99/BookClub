package book.chat.meeting.entity;


import book.chat.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Meeting.MeetingId> {

    List<Meeting> findTop10ByOrderByMeetingDateDesc();
    List<Meeting> findAll();
//    List<Meeting> findAllByDate(LocalDate meetingDate);
    List<Meeting> findAllByIdClubNo(long clubNo);
    List<Meeting> findByOnline(String type);

    Meeting save(Meeting meeting);
//    Meeting findByNo(long meetingNo);
}
