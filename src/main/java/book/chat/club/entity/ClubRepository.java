package book.chat.club.entity;


import book.chat.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Club save(Club entity);
    Optional<Club> findByNo(Long no);
    List<Club> findAll();
    List<Club> findByLocationOrderByName(String location);
    List<Club> findByName(String name);
    List<Club> findTop4ByOrderByStartDateDesc();

}
