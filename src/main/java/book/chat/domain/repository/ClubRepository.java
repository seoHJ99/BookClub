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
    List<Club> findAllByInterval(int interval);
    List<Club> findAllByMeetingDay(String day);
    List<Club> findByClubName(String name);

    Club findByClubId(Long id);

    List<Club> findByMemberJoin(Long memberId);
}
