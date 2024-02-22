package book.chat.domain.service;

import book.chat.domain.entity.Club;
import book.chat.domain.repository.ClubRepository;
import book.chat.web.DTO.ClubDTO;
import book.chat.web.DTO.ClubMakingForm;
import book.chat.web.DTO.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    public ClubDTO findClubByNo(Long clubNo){
        return new ClubDTO(clubRepository.findByNo(clubNo).orElse(null));
    }
//
//    public List<ClubDTO> findAsMuchAsLimit(int limit){
//        return clubRepository.findAsMuchAsLimit(limit).stream()
//                .map(entity -> new ClubDTO(entity))
//                .collect(Collectors.toList());
//    }

    public ClubDTO save(ClubMakingForm newClub, MemberDTO leader){
        Club savedEntity = clubRepository.save(new Club(newClub, leader));
        return new ClubDTO(savedEntity);
    }

    public List<ClubDTO> findAll(){
        return clubRepository.findAll().stream()
                .map(ClubDTO::new)
                .collect(Collectors.toList());
    }
}
