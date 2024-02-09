package book.chat.domain.repository;

import book.chat.domain.entity.Meeting;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MeetingRepositoryImpl implements MeetingRepository{

    private long sequence =0L;

    private static final Map<Long, Meeting> store = new HashMap<>(); //static

    public Meeting save(Meeting entity){
        store.put(++sequence, entity);
        return entity;
    }

    public Optional<Meeting> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Meeting> findAll() {
        return null;
    }

    @Override
    public List<Meeting> findAllByDate(LocalDate meetingDate) {
        return null;
    }

    @Override
    public List<Meeting> findAllByClub(long clubNo) {
        return null;
    }

    @Override
    public List<Meeting> findByType(String type) {
        return null;
    }

    @Override
    public Meeting findByNo(long meetingNo) {
        return null;
    }
}
