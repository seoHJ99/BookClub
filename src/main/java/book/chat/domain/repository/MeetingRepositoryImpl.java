package book.chat.domain.repository;

import book.chat.domain.DTO.MeetingDto;
import book.chat.domain.DTO.ReviewDTO;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MeetingRepositoryImpl implements MeetingRepository{

    private long sequence =0L;

    private static final Map<Long, MeetingDto> store = new HashMap<>(); //static

    public MeetingDto save(MeetingDto entity){
        store.put(++sequence, entity);
        return entity;
    }

    public Optional<MeetingDto> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

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
