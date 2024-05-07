package book.chat.member.controller;

import book.chat.api.aws.AwsS3Service;
import book.chat.board.dto.CommentDTO;
import book.chat.board.dto.ReviewDTO;
import book.chat.board.service.BoardService;
import book.chat.board.service.CommentService;
import book.chat.common.ApiMessageConst;
import book.chat.common.SessionConst;
import book.chat.common.dto.BookDTO;
import book.chat.member.dto.MemberDTO;
import book.chat.member.dto.MemberJoinForm;
import book.chat.member.service.MemberService;
import book.chat.redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberService memberService;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;
    private final AwsS3Service awsS3Service;
    private final BoardService boardService;
    private final CommentService commentService;


    @PostMapping("/duplicate/id")
    public ResponseEntity<String> idCheck(@RequestParam("id") String id) {

        MemberDTO memberDTO = memberService.findById(id);

        if (memberDTO != null || redisService.existId(id)) {
            return new ResponseEntity<>(ApiMessageConst.ALREADY_EXIST, HttpStatus.CONFLICT);
        }

        Cookie cookie = new Cookie("idCheck", id);
        cookie.setPath("/v1/member");
        cookie.setMaxAge(300); // 5분간 유지되는 쿠키
        redisService.idSave(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok()
                .headers(headers)
                .body("{\"id\" : \"" + id + "\"}");
    }

    @PostMapping(path = "/join")
    public ResponseEntity<String> join(@Validated @ModelAttribute MemberJoinForm memberJoinForm,
                                       @CookieValue(value = "idCheck", required = false) String idCheck,
                                       @RequestPart(value = "profileImg", required = false) MultipartFile file) {

        memberJoinForm.setProfile(file);
        memberJoinForm.setMail(memberJoinForm.getEmail() + "@" + memberJoinForm.getEmail2());
        String x = validationCheck(memberJoinForm, idCheck);
        if (x != null) return new ResponseEntity<>(ApiMessageConst.NOT_ALLOWED, HttpStatus.BAD_REQUEST);

        if (memberJoinForm.getProfile() != null) {
            String uploadURL = awsS3Service.upload(memberJoinForm.getProfile());
            memberJoinForm.setProfileURL(uploadURL);
        }
        MemberDTO save = memberService.save(memberJoinForm);
        String data;
        try {
            data = objectMapper.writeValueAsString(save);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/info/{member}")
    public ResponseEntity<String> memberUpdateForm(@PathVariable("member") String memberId, @RequestParam("pw") String pw) {
        MemberDTO memberDTO = memberService.findById(memberId);
        if(memberDTO != null && memberDTO.getPw().equals(pw)){
            try {
                return new ResponseEntity<>(objectMapper.writeValueAsString(memberDTO), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                log.info(e.getMessage());
                return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(ApiMessageConst.NOT_ALLOWED, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/board/{member}")
    public ResponseEntity<String> memberWriteBoard(@PathVariable("member") String memberId, HttpSession session, Model model) {
        List<ReviewDTO> reviews = boardService.findByWriter(memberId);
        if(reviews != null){
            try {
                return new ResponseEntity<>(objectMapper.writeValueAsString(reviews), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                log.info(e.getMessage());
                return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(ApiMessageConst.NO_DATA, HttpStatus.BAD_REQUEST) ;
    }

    @GetMapping("/comment/{member}")
    public ResponseEntity<String> memberComment(@PathVariable("member") String memberId, @RequestParam("pw") String pw) {
        MemberDTO memberDTO = memberService.findById(memberId);
        if(memberDTO == null){
            return new ResponseEntity<>(ApiMessageConst.NOT_A_MEMBER, HttpStatus.UNAUTHORIZED);
        }
        if( memberDTO.getPw().equals(pw)) {
            List<CommentDTO> comments = commentService.findByWriter(memberId);
            try {
                return new ResponseEntity<>(objectMapper.writeValueAsString(comments), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                return new ResponseEntity<>(ApiMessageConst.WRONG_PARAMETER, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(ApiMessageConst.NOT_ALLOWED, HttpStatus.FORBIDDEN);
    }

    private String validationCheck(MemberJoinForm memberJoinForm, String idCheck) {
        if (idCheck == null) {
            return "layout/member-join";
        }
        if (!memberJoinForm.getId().equals(idCheck)) {
            return "layout/member-join";
        }
        // todo 메일도 똑같이.
        if (!pwIsSame(memberJoinForm)) {
            return "layout/member-join";
        }

        return null;
    }


    public boolean pwIsSame(MemberJoinForm memberJoinForm) {
        return memberJoinForm.getPw().equals(memberJoinForm.getPw2());
    }
}
