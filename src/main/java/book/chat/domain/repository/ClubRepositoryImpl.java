package book.chat.domain.repository;

import book.chat.domain.DTO.ClubDTO;
import book.chat.domain.DTO.MeetingDto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ClubRepositoryImpl implements ClubRepository{

    private long sequence =0L;

    private static final Map<Long, ClubDTO> store = new HashMap<>(); //static

    public ClubDTO save(ClubDTO entity){
        store.put(++sequence, entity);
        return entity;
    }

    public Optional<ClubDTO> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<ClubDTO> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<ClubDTO> findAllByLocation(String location) {
        // todo 지역명으로 검색
        return null;
    }

    @Override
    public List<ClubDTO> findAllByInterval(String interval) {
        return null;
    }

    @Override
    public List<ClubDTO> findAllByMeetingDay(String day) {
        return null;
    }

    @Override
    public ClubDTO findByClubId(Long id) {
        return null;
    }

    @Override
    public ClubDTO findByClubName(String name) {
        return null;
    }

    @Override
    public List<ClubDTO> findByMemberJoin(Long memberId) {
        return null;
    }
}
