package book.chat.member.controller;

import book.chat.api.aws.AwsS3Service;
import book.chat.api.naver.BookSearchAPI;
import book.chat.board.dto.CommentDTO;
import book.chat.board.dto.ReviewDTO;
import book.chat.board.service.BoardService;
import book.chat.board.service.CommentService;
import book.chat.common.SessionConst;
import book.chat.common.dto.BookDTO;
import book.chat.member.dto.MemberDTO;
import book.chat.member.dto.MemberJoinForm;
import book.chat.member.dto.UpdateForm;
import book.chat.member.service.MemberService;
import book.chat.redis.service.RedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final RedisService redisService;
    private final BoardService reviewService;
    private final BookSearchAPI bookSearchAPI;
    private final CommentService commentService;
    private final AwsS3Service awsS3Service;

    @GetMapping("/join")
    public String joinPage(@ModelAttribute MemberJoinForm memberJoinForm) {
        return "layout/member-join";
    }

    /**
     * id 중복 체크.
     * 중복 체크 요청이 들어오면 db에서 해당 id가 존재하는지 확인.
     * 이후 redis에서 다시 체크. 동시에 회원가입 진행중, 같은 id로 중복 체크 통과한 사람이 존재하는지 확인.
     * 만약 redis 에도 없으면 해당 id를 redis에 5분간 저장하고 5분간 유지되는 쿠키를 내려줌.
     * 이후 회원가입 진행시, 쿠키가 존재하면 id중복 체크 통과한걸로 판단하고 계속 진행
     */
    @ResponseBody
    @PostMapping("/id/check")
    public String checkIdDuplicate(@RequestParam("id") String id,
                                   HttpServletResponse response,
                                   HttpServletRequest request) throws IOException {

        MemberDTO memberDTO = memberService.findById(id);

        if (memberDTO != null || redisService.existId(id)) {
            return "<script>alert('이미 존재하는 id입니다.'); </script>";
        }

//        String uuid = UUID.randomUUID().toString();
//        Cookie cookie = new Cookie("idCheck", uuid);
        Cookie cookie = new Cookie("idCheck", id);
        cookie.setPath("/member");
        cookie.setMaxAge(300); // 5분간 유지되는 쿠키
        response.addCookie(cookie);
        redisService.idSave(id);
        return "<script>alert('중복 체크 통과'); </script>";
    }

    @PostMapping(path = "/join", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String join(@Validated @ModelAttribute MemberJoinForm memberJoinForm,
                       BindingResult bindingResult,
                       @CookieValue(value = "idCheck", required = false) String idCheck,
                       @CookieValue(value = "emailCheck", required = false) String mailCheck) {

        memberJoinForm.setMail(memberJoinForm.getEmail() + "@" + memberJoinForm.getEmail2());
        String x = validationCheck(memberJoinForm, bindingResult, idCheck);
        if (x != null) return x;

        if (memberJoinForm.getProfile() != null) {
            String uploadURL = awsS3Service.upload(memberJoinForm.getProfile());
            memberJoinForm.setProfileURL(uploadURL);
        }
        memberService.save(memberJoinForm);
        return "redirect:/";
    }

    private String validationCheck(MemberJoinForm memberJoinForm, BindingResult bindingResult, String idCheck) {
        if (idCheck == null) {
            bindingResult.reject("noDoubleCheck", null, null);
            return "layout/member-join";
        }

        if (!memberJoinForm.getId().equals(idCheck)) {
            bindingResult.reject("idDoubleCheck", null, null);
            return "layout/member-join";
        }
        if (!pwIsSame(memberJoinForm)) {
            bindingResult.reject("pwDoubleCheck", null, null);
            return "layout/member-join";
        }
        if (bindingResult.hasErrors()) {
            return "layout/member-join";
        }
        return null;
    }


    public boolean pwIsSame(MemberJoinForm memberJoinForm) {
        return memberJoinForm.getPw().equals(memberJoinForm.getPw2());
    }

    @GetMapping("/info")
    public String memberInfo(Model model, HttpSession session) {
        model.addAttribute("member", session.getAttribute(SessionConst.LOGIN_MEMBER));
        return "layout/member-info";
    }

    @GetMapping(path = "/info/{member}")
    public String memberUpdateForm(@PathVariable("member") String memberId,
                                   Model model,
                                   HttpSession session,
                                   HttpServletResponse response) throws IOException {
        MemberDTO memberDTO = memberService.findById(memberId);
        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (isNotSame(memberDTO, loginMember)) {
            response.sendError(HttpStatus.FORBIDDEN.value());
        }
        model.addAttribute("member", memberDTO);
        return "layout/member-update";
    }

    @PostMapping(path = "/info/{member}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String memberInfoUpdate(@PathVariable("member") String id,
                                   @Validated @ModelAttribute("member") UpdateForm updateForm,
                                   BindingResult bindingResult, HttpSession session) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (bindingResult.hasErrors()) {
            return "layout/member-update";
        }
        MemberDTO member = memberService.updateMemberInfo(updateForm, loginMember);
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return "redirect:/member/info/" + loginMember.getId();
    }

    @GetMapping("/board/{member}")
    public String memberWriteBoard(@PathVariable("member") String memberId, HttpSession session, Model model) {
        List<ReviewDTO> reviews = reviewService.findByWriter(memberId);
        List<BookDTO> bookDTOS = reviews.stream()
                .map(dto -> bookSearchAPI.bookSearch(dto.getIsbn()).get(0))
                .collect(Collectors.toList());
        model.addAttribute("boards", reviews);
        model.addAttribute("books", bookDTOS);
        return "layout/member-boards";
    }

    @GetMapping("/comment/{member}")
    public String memberComment(@PathVariable("member") String memberId, Model model, HttpSession session) {
        List<CommentDTO> comments = commentService.findByWriter(memberId);
        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("clubComments", commentService.findClubCommentById(loginMember.getId()));
        model.addAttribute("comments", comments);
        return "layout/member-comment";
    }

    private static boolean isNotSame(MemberDTO memberDTO, MemberDTO loginMember) {
        return !memberDTO.equals(loginMember);
    }
}