package book.chat.domain.repository;


import book.chat.domain.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Club save(Club entity);
    Optional<Club> findByNo(Long no);
//    List<Club> findAsMuchAsLimit(int limit);
    List<Club> findAll();
    List<Club> findAllByLocation(String location);
//    List<Club> findAllByInterval(int interval);
//    List<Club> findAllByMeetingDay(String day);
    List<Club> findByName(String name);

//    Club findByNo(Long no);

//    List<Club> findByMemberJoin(Long memberId);
}
