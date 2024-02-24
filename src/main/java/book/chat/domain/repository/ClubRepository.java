package book.chat.domain.repository;


import book.chat.domain.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Club save(Club entity);
    Optional<Club> findByNo(Long no);
    List<Club> findAll();
    List<Club> findByLocationOrderByName(String location);
    List<Club> findByName(String name);
    List<Club> findTop4OrderByStartDateDesc();

}
