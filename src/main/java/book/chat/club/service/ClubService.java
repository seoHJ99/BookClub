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


    /**
     * [클럽 번호로 클럽 찾기]
     * @param clubNo (클럽 번호)
     * @return clubDTO (클럽 DTO )
     * */
    public ClubDTO findClubByNo(Long clubNo) {
        return new ClubDTO(clubRepository.findByNo(clubNo).orElse(null));
    }

    /**
     * [새로운 클럽 db저장]
     * @param newClub (새롭게 만들 클럽 dto)
     * @param leaderNo (클럽을 만드는 클럽장)
     * @return 새롭게 만들어진 클럽 (clubDTO)
     * */
    @Transactional
    public ClubDTO save(ClubMakingForm newClub, Long leaderNo) {
        String url = "https://image.utoimage.com/preview/cp872722/2022/12/202212008462_500.jpg";

        if(!newClub.getProfile().isEmpty()){
            url = awsS3Service.upload(newClub.getProfile());
        }
        newClub.setProfileUrl(url);

        Club savedEntity = clubRepository.save(new Club(newClub, leaderNo));
        return new ClubDTO(savedEntity);
    }

    /**
     * [모든 클럽 찾기]
     * @return 모든 클럽 리스트 (List)
     * */
    public List<ClubDTO> findAll() {
        return transferToDtoList(clubRepository.findAll());
    }

    /**
     * [최근 만들어진 클럽 4개 찾기]
     * @return 최근 개설된 4개 클럽 리스트 (List)
     * */
    public List<ClubDTO> findRecent4Club() {
        return transferToDtoList(clubRepository.findTop4ByOrderByStartDateDesc());
    }

    /**
     * [모임 맴버 찾기]
     * @param clubDTO (모임 dto)
     * @return 모임 맴버 리스트 (List)
     * */
    public List<MemberDTO> findClubMember(ClubDTO clubDTO) {
        List<Long> memberList = clubDTO.getMembers();
        List<Member> clubMember = memberRepository.findByNoIn(memberList);
        return clubMember.stream()
                .map(MemberDTO::new)
                .collect(Collectors.toList());
    }


    /**
     * [모임에서 최근 읽은 책 10권을 네이버 api를 이용해 정보를 가져옴]
     * @param bookList (모임에서 읽은 책 리스트)
     * @return 네이버 책 검색 api를 이용해 찾은 10개의 책 검색 결과 (List)
     * @see BookSearchAPI#bookSearch(String keyword)
     * */
    public List<BookDTO> findReadBooksLimit10(List<String> bookList) {
        if (bookList.size() == 0) {
            return null;
        }
        List<BookDTO> books = bookList.stream()
                .map(isbn -> {
                    try{
                       return bookSearchAPI.bookSearch(isbn).get(0);
                    }catch (IndexOutOfBoundsException ex){
                        return null;
//                        return BookDTO.builder()
//                                .author("알 수 없는 작가")
//                                .image("/free-icon-book-828370.png")
//                                .name("알수 없음")
//                                .isbn(isbn)
//                                .description("없음")
//                                .shoppingLink("없음")
//                                .publisher("없음")
//                                .publishDate(null)
//                                .build();
                    }
                })
                .limit(10)
                .toList();
        return books;
    }

    /**
     * [클럽 엔티티 리스트 => 클럽dto 리스트로 전환]
     * @param entities (클럽 엔티티 list)
     * @return 클럽dto 리스트 (List)
     * */
    public List<ClubDTO> transferToDtoList(List<Club> entities) {
        return entities.stream()
                .map(entity -> new ClubDTO(entity))
                .collect(Collectors.toList());
    }

    /**
     * [클럽 정보 업데이트]
     * @param clubDTO (업데이트할 클럽 정보)
     * @return 업데이트 된 clubDTO (ClubDTO)
     * */
    @Transactional
    public ClubDTO updateClubInfo(ClubDTO clubDTO) {
        clubRepository.save(new Club(clubDTO));
        return clubDTO;
    }

    /**
     * [클럽에 신규 맴버 가입]
     * @param memberId (가입하려는 사용자 id)
     * @param clubNo (가입하려는 모임 번호)
     * */
    @Transactional
    public ClubDTO joinMember(String memberId, Long clubNo) {
        ClubDTO club = findClubByNo(clubNo);
        MemberDTO member = memberService.findById(memberId);
        club.getMembers().add(member.getNo());
        MemberDTO newMember = memberService.findByNo(member.getNo());
        newMember.getJoinClub().add(clubNo);
        memberService.updateMemberInfo(newMember, member.getNo());
        ClubDTO updated = updateClubInfo(club);
        return updated;
    }
}
