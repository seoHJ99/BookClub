package book.chat.domain.service;

import book.chat.domain.entity.Club;
import book.chat.domain.repository.ClubRepository;
import book.chat.web.DTO.ClubDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    public ClubDTO findClubByNo(Long clubNo){
        return new ClubDTO(clubRepository.findByClubId(clubNo));
    }

    public List<ClubDTO> findAsMuchAsLimit(int limit){
        return clubRepository.findAsMuchAsLimit(limit).stream()
                .map(entity -> new ClubDTO(entity))
                .collect(Collectors.toList());
    }

    public ClubDTO makeNew(ClubDTO newClub){
        Club savedEntity = clubRepository.save(new Club(newClub));
        return new ClubDTO(savedEntity);
    }
}
