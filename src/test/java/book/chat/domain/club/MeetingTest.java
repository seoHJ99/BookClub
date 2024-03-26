package book.chat.domain.club;


import book.chat.meeting.entity.Meeting;
import book.chat.meeting.entity.MeetingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MeetingTest {

    @Autowired
    private MeetingRepository meetingRepository;

    @Test
    void meetingTimeSelectTest(){
        List<Meeting> allNotDoneMeeting = meetingRepository.findAllNotDoneMeeting(1l, LocalDate.now());
        assertThatCode(()->meetingRepository.findAllNotDoneMeeting(1l, LocalDate.now())).doesNotThrowAnyException();
    }

    @Test
    void meetingTest(){
        List<Meeting> first = meetingRepository.findTop10ByMeetingDateGreaterThanEqualOrderByMeetingDateDesc(LocalDate.now());
        for (Meeting meeting : first) {
            System.out.println(meeting);
        }

        List<Meeting> second = meetingRepository.findFirst10ByIdClubNoAndMeetingDateGreaterThanEqualOrderByMeetingDateDesc(1L, LocalDate.now());
        for (Meeting meeting : second) {
            System.out.println(meeting);
        }
    }
}
