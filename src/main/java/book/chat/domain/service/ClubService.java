package book.chat.domain.service;

import book.chat.domain.DTO.ClubDTO;
import book.chat.domain.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    public ClubDTO findClubByNo(Long clubNo){
        return clubRepository.findByClubId(clubNo);
    }

    public List<ClubDTO> findAsMuchAsLimit(int limit){
        return clubRepository.findAsMuchAsLimit(limit);
    }
}
