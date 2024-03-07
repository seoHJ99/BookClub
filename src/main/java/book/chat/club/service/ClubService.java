package book.chat.club.service;

import book.chat.api.naver.BookSearchAPI;
import book.chat.club.dto.ClubDTO;
import book.chat.club.dto.ClubMakingForm;
import book.chat.club.entity.Club;
import book.chat.club.entity.ClubRepository;
import book.chat.member.service.MemberService;
import book.chat.member.entity.Member;
import book.chat.member.entity.MemberRepository;
import book.chat.common.dto.BookDTO;
import book.chat.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final BookSearchAPI bookSearchAPI;

    public ClubDTO findClubByNo(Long clubNo) {
        return new ClubDTO(clubRepository.findByNo(clubNo).orElse(null));
    }

    @Transactional
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
        if(bookList.size() == 0){
            return null;
        }
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

    @Transactional
    public ClubDTO updateClubInfo(ClubDTO clubDTO){
        clubRepository.save(new Club(clubDTO));
        return clubDTO;
    }

    @Transactional
    public void joinMember(Long memberNo, Long clubNo){
        ClubDTO club = findClubByNo(clubNo);
        club.getMembers().add(memberNo);
        MemberDTO member = memberService.findByNo(memberNo);
        member.getJoinClub().add(clubNo);
        memberService.updateMemberInfo(member);
        updateClubInfo(club);
    }
}
