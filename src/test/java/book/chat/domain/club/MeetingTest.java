package book.chat.domain.club;


import book.chat.meeting.entity.Meeting;
import book.chat.meeting.entity.MeetingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
}
