package book.chat.meeting.entity;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Meeting.MeetingId> {

    List<Meeting> findTop10ByMeetingDateGreaterThanEqualOrderByMeetingDateDesc(LocalDate date);
    List<Meeting> findAll();
//    List<Meeting> findAllByDate(LocalDate meetingDate);
    List<Meeting> findFirst10ByIdClubNoAndMeetingDateGreaterThanEqualOrderByMeetingDateDesc(long clubNo, LocalDate date);
//                  findFirst10ByIdClubNoAndMeetingDateGreaterThanEqualOrderByMeetingDateDesc
    List<Meeting> findByOnline(String type);

    Meeting findByIdClubNoAndIdNo(Long no, Long clubNo);

    Meeting save(Meeting meeting);
//    Meeting findByNo(long meetingNo);

    @Query("SELECT m FROM Meeting m WHERE m.id.clubNo = :no AND m.meetingDate >= :now")
    List<Meeting> findAllNotDoneMeeting(@Param("no") Long no, @Param("now") LocalDate now);
}
