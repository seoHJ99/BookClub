package book.chat.domain.repository;


import book.chat.domain.entity.Club;

import java.util.List;
import java.util.Optional;

public interface ClubRepository {
    Club save(Club entity);
    Optional<Club> findByNo(Long no);
    List<Club> findAsMuchAsLimit(int limit);
    List<Club> findAll();
    List<Club> findAllByLocation(String location);
    List<Club> findAllByInterval(String interval);
    List<Club> findAllByMeetingDay(String day);

    Club findByClubId(Long id);
    Club findByClubName(String name);

    List<Club> findByMemberJoin(Long memberId);
}
