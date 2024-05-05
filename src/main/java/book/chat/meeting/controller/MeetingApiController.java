package book.chat.meeting.controller;

import book.chat.common.ApiMessageConst;
import book.chat.meeting.dto.MeetingDto;
import book.chat.meeting.service.MeetingService;
import book.chat.member.dto.MemberDTO;
import book.chat.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/meeting")
public class MeetingApiController {

    private final MeetingService meetingService;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;

    @GetMapping("/{no}/club/{clubNo}")
    public ResponseEntity<String> getMeetingInfo(@PathVariable("no") Long no, @PathVariable("clubNo") Long clubNo){
        try {
            MeetingDto meeting = meetingService.findByClubNoAndNo(clubNo, no);
            return new ResponseEntity<>(objectMapper.writeValueAsString(meeting), HttpStatus.BAD_REQUEST);
        }catch (NullPointerException e){
            return new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/club/{clubNo}")
    public ResponseEntity<String> creatingMeeting(@PathVariable("clubNo") Long clubNo, MeetingDto meetingDto,
                                                  @RequestParam("id") String id, @RequestParam("pw") String pw){
        boolean memExist = memberService.checkMember(id, pw);
        if(!memExist){
            return new  ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
        }
        MemberDTO member = memberService.findById(id);
        if(!member.getJoinClub().contains(clubNo)){
            return new ResponseEntity<>(ApiMessageConst.NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        try {
            MeetingDto meeting = meetingService.save(meetingDto,member);
            return new ResponseEntity<>(objectMapper.writeValueAsString(meeting), HttpStatus.BAD_REQUEST);
        }catch (PersistenceException e){
            return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/attending/{no}/club/{clubNo}")
    public ResponseEntity<String> joinMeeting(@PathVariable("no") Long no, @PathVariable("clubNo") Long clubNo,
                                              @RequestParam("id") String id, @RequestParam("pw") String pw){
        boolean memExist = memberService.checkMember(id, pw);
        if(!memExist){
            return new  ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
        }
        MemberDTO member = memberService.findById(id);
        if(!member.getJoinClub().contains(clubNo)){
            return new ResponseEntity<>(ApiMessageConst.NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        try {
            MeetingDto meeting = meetingService.join(member, clubNo, no);
            return new ResponseEntity<>(objectMapper.writeValueAsString(meeting), HttpStatus.BAD_REQUEST);
        }catch (NullPointerException e){
            return new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/canceling/{no}/club/{clubNo}")
    public ResponseEntity<String> outMeeting(@PathVariable("no") Long no, @PathVariable("clubNo") Long clubNo,
                                              @RequestParam("id") String id, @RequestParam("pw") String pw){
        boolean memExist = memberService.checkMember(id, pw);
        if(!memExist){
            return new  ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
        }
        MemberDTO member = memberService.findById(id);
        if(!member.getJoinClub().contains(clubNo)){
            return new ResponseEntity<>(ApiMessageConst.NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        try {
            MeetingDto meeting = meetingService.out(member, clubNo, no);
            return new ResponseEntity<>(objectMapper.writeValueAsString(meeting), HttpStatus.BAD_REQUEST);
        }catch (NullPointerException e){
            return new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
    }
}
