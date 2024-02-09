package book.chat.domain.repository;

import book.chat.domain.entity.Club;
import book.chat.web.DTO.ClubDTO;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ClubRepositoryImpl implements ClubRepository{

    private long sequence =0L;

    private static final Map<Long, Club> store = new HashMap<>(); //static

    @Override
    public Club save(Club entity){
        store.put(++sequence, entity);
        return entity;
    }

    @Override
    public Optional<Club> findByNo(Long no){
        return Optional.ofNullable(store.get(no));
    }

    @Override
    public List<Club> findAsMuchAsLimit(int limit) {
        return null;
    }

    @Override
    public List<Club> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Club> findAllByLocation(String location) {
        // todo 지역명으로 검색
        return null;
    }

    @Override
    public List<Club> findAllByInterval(String interval) {
        return null;
    }

    @Override
    public List<Club> findAllByMeetingDay(String day) {
        return null;
    }

    @Override
    public Club findByClubId(Long id) {
        return null;
    }

    @Override
    public Club findByClubName(String name) {
        return null;
    }

    @Override
    public List<Club> findByMemberJoin(Long memberId) {
        return null;
    }
}
