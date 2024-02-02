package book.chat.domain.service;

import book.chat.domain.DTO.ClubDTO;
import book.chat.domain.entity.Club;
import book.chat.domain.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    public ClubDTO save(ClubDTO newClub){
        Club savedEntity = clubRepository.save(new Club(newClub));
        return new ClubDTO(savedEntity);
    }
}