package book.chat.domain.repository;

import book.chat.domain.DTO.ClubDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClubRepositoryImpl implements ClubRepository{

    private static final Map<Long, ClubDTO> store  = new HashMap<>(); //static

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
