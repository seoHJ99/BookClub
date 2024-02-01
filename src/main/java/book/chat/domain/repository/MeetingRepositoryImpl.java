package book.chat.domain.repository;

import book.chat.domain.DTO.MeetingDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class MeetingRepositoryImpl implements MeetingRepository{

    @Override
    public List<MeetingDto> findAll() {
        return null;
    }

    @Override
    public List<MeetingDto> findAllByDate(LocalDate meetingDate) {
        return null;
    }

    @Override
    public List<MeetingDto> findAllByClub(long clubNo) {
        return null;
    }

    @Override
    public MeetingDto findByNo(long meetingNo) {
        return null;
    }
}
