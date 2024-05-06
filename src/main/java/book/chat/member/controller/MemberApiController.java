package book.chat.member.controller;

import book.chat.api.aws.AwsS3Service;
import book.chat.common.ApiMessageConst;
import book.chat.member.dto.MemberDTO;
import book.chat.member.dto.MemberJoinForm;
import book.chat.member.service.MemberService;
import book.chat.redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberService memberService;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;
    private final AwsS3Service awsS3Service;


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
                       @RequestPart(value = "profileImg", required = false)MultipartFile file) {

        memberJoinForm.setProfile(file);
        memberJoinForm.setMail(memberJoinForm.getEmail() + "@" + memberJoinForm.getEmail2());
        String x = validationCheck(memberJoinForm, idCheck);
        if (x != null) return new ResponseEntity<>(ApiMessageConst.NOT_ALLOWED, HttpStatus.BAD_REQUEST);

        if(memberJoinForm.getProfile() != null){
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
