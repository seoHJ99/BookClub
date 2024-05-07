package book.chat.club.controller;

import book.chat.club.dto.ClubDTO;
import book.chat.club.dto.ClubMakingForm;
import book.chat.club.service.ClubService;
import book.chat.common.ApiMessageConst;
import book.chat.member.dto.MemberDTO;
import book.chat.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/club")
@Slf4j
public class ClubApiController {

    private final ClubService clubService;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;


    /**
     * ["GET /v1/club/:no" <br/>
     * 클럽 정보 조회.]
     * @param no (클럽 번호)
     * */
    @GetMapping("/{no}")
    public ResponseEntity<String> getClubInfo(@PathVariable("no") Long no) {
        ClubDTO club = null;
        String data = "";
        try {
            club = clubService.findClubByNo(no);
            data = objectMapper.writeValueAsString(club);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * ["PATCH /v1/club/{no}" <br/>
     * 클럽 가입 요청 처리]
     * @param no (클럽 번호)
     * @param id (가입하려는 사용자 id)
     * @param pw (가입하려는 사용자 pw)
     *
     * */
    @PatchMapping("/{no}")
    public ResponseEntity<String> joinClub(@PathVariable("no") Long no,
                                           @RequestParam("id") String id, @RequestParam("pw") String pw) {
        ClubDTO club = null;
        String data = "";
        if (!memberService.checkMember(id, pw)) {
            return new ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
        }
        MemberDTO member = memberService.findById(id);
        try {
            club = clubService.joinMember(member.getId(), no);
            data = objectMapper.writeValueAsString(club);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * ["POST /v1/club" <br/>
     * 클럽 생성 요청 처리]
     * @param id (가입하려는 사용자 id)
     * @param pw (가입하려는 사용자 pw)
     * @param makingForm (생성하려는 클럽 데이터)
     * */
    @PostMapping("")
    public ResponseEntity<String> makeClub(@RequestParam("id") String id,
                                           @RequestParam("pw") String pw,
                                           @RequestParam ClubMakingForm makingForm) {
        ClubDTO club = null;
        String data = "";
        if (!memberService.checkMember(id, pw)) {
            return new ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
        }
        MemberDTO member = memberService.findById(id);
        try {
            club = clubService.save(makingForm, member.getNo());
            data = objectMapper.writeValueAsString(club);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
