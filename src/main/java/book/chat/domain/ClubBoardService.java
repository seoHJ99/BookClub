package book.chat.domain;

import book.chat.domain.DTO.ClubDTO;
import book.chat.domain.entity.Club;
import book.chat.domain.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubBoardService {

    private final ClubRepository clubRepository;

    public List<ClubDTO> findClubsByName(String keyword){
        return transferToDtoList( clubRepository.findByClubName(keyword));
    }

    public List<ClubDTO> findClubByLocation(String location){
        return transferToDtoList( clubRepository.findAllByLocation(location) );
    }

    public List<ClubDTO> transferToDtoList(List<Club> entities){
        return entities.stream()
                .map(entity -> new ClubDTO(entity))
                .collect(Collectors.toList());
    }

    public List<ClubDTO> findByInterval(int intervalDay){
        return transferToDtoList( clubRepository.findAllByInterval(intervalDay) );
    }


    public List <ClubDTO> findMemberJoinedClubs(Long memberNo){
        return transferToDtoList(clubRepository.findByMemberJoin(memberNo));
    }

    public ClubDTO findById(Long id){
        return new ClubDTO( clubRepository.findById(id).orElse(null) );
    }

}
