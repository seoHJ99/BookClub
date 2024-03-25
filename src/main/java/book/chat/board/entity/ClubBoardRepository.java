package book.chat.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubBoardRepository extends JpaRepository<ClubBoard, Long> {
    List<ClubBoard> findAllLimit10ByClubNoOrderByWriteDateDesc(Long no);
    List<ClubBoard> findAllByClubNoOrderByWriteDateDesc(Long no);

    ClubBoard findByNo(Long no);
}
