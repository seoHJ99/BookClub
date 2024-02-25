package book.chat.domain.service;

import book.chat.domain.api.naver.BookSearchAPI;
import book.chat.domain.entity.Club;
import book.chat.domain.entity.Member;
import book.chat.domain.repository.ClubRepository;
import book.chat.domain.repository.MemberRepository;
import book.chat.web.DTO.BookDTO;
import book.chat.web.DTO.ClubDTO;
import book.chat.web.DTO.ClubMakingForm;
import book.chat.web.DTO.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final BookSearchAPI bookSearchAPI;

    public ClubDTO findClubByNo(Long clubNo) {
        return new ClubDTO(clubRepository.findByNo(clubNo).orElse(null));
    }

    public ClubDTO save(ClubMakingForm newClub, MemberDTO leader) {
        Club savedEntity = clubRepository.save(new Club(newClub, leader));
        return new ClubDTO(savedEntity);
    }

    public List<ClubDTO> findAll() {
        return transferToDtoList(clubRepository.findAll());
    }

    public List<ClubDTO> findRecent4Club() {
        return transferToDtoList(clubRepository.findTop4ByOrderByStartDateDesc());
    }

    public List<MemberDTO> findClubMember(ClubDTO clubDTO) {
        List<Long> memberList = clubDTO.getMembers();
        List<Member> clubMember = memberRepository.findByNoIn(memberList);
        return clubMember.stream()
                .map(MemberDTO::new)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findReadBooksLimit10(List<String> bookList) {
        return bookList.stream()
                .map(isbn -> bookSearchAPI.bookSearch(isbn).get(0))
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<ClubDTO> transferToDtoList(List<Club> entities) {
        return entities.stream()
                .map(entity -> new ClubDTO(entity))
                .collect(Collectors.toList());
    }

}
