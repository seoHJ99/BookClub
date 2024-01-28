package book.chat.domain.repository;

import book.chat.domain.DTO.ClubDTO;

import java.util.List;

public interface ClubRepository {
    List<ClubDTO> findAll();
    List<ClubDTO> findAllByLocation(String location);
    List<ClubDTO> findAllByInterval(String interval);
    List<ClubDTO> findAllByMeetingDay(String day);

    ClubDTO findByClubId(Long id);
    ClubDTO findByClubName(String name);

    List<ClubDTO> findByMemberJoin(Long memberId);
}
