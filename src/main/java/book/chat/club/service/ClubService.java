package book.chat.club.service;

import book.chat.api.aws.AwsS3Service;
import book.chat.api.naver.BookSearchAPI;
import book.chat.club.dto.ClubDTO;
import book.chat.club.dto.ClubMakingForm;
import book.chat.club.entity.Club;
import book.chat.club.entity.ClubRepository;
import book.chat.common.dto.BookDTO;
import book.chat.member.dto.MemberDTO;
import book.chat.member.entity.Member;
import book.chat.member.entity.MemberRepository;
import book.chat.member.service.MemberService;
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
    private final AwsS3Service awsS3Service;


    public ClubDTO findClubByNo(Long clubNo) {
        return new ClubDTO(clubRepository.findByNo(clubNo).orElse(null));
    }

    @Transactional
    public ClubDTO save(ClubMakingForm newClub, MemberDTO leader) {
        String url = "https://image.utoimage.com/preview/cp872722/2022/12/202212008462_500.jpg";

        if(!newClub.getProfile().isEmpty()){
            url = awsS3Service.upload(newClub.getProfile());
        }
        newClub.setProfileUrl(url);

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
        if (bookList.size() == 0) {
            return null;
        }
        List<BookDTO> books = bookList.stream()
                .map(isbn -> {
                    try{
                       return bookSearchAPI.bookSearch(isbn).get(0);
                    }catch (IndexOutOfBoundsException ex){
                        return BookDTO.builder()
                                .author("알 수 없는 작가")
                                .image("/free-icon-book-828370.png")
                                .name("알수 없음")
                                .isbn(isbn)
                                .description("없음")
                                .shoppingLink("없음")
                                .publisher("없음")
                                .publishDate(null)
                                .build();
                    }
                })
                .limit(10)
                .toList();
        return books;
    }

    public List<ClubDTO> transferToDtoList(List<Club> entities) {
        return entities.stream()
                .map(entity -> new ClubDTO(entity))
                .collect(Collectors.toList());
    }

    @Transactional
    public ClubDTO updateClubInfo(ClubDTO clubDTO) {
        clubRepository.save(new Club(clubDTO));
        return clubDTO;
    }

    @Transactional
    public void joinMember(MemberDTO member, Long clubNo) {
        ClubDTO club = findClubByNo(clubNo);
        club.getMembers().add(member.getNo());
        MemberDTO newMember = memberService.findByNo(member.getNo());
        newMember.getJoinClub().add(clubNo);
        memberService.updateMemberInfo(newMember, member);
        updateClubInfo(club);
    }
}
