package book.chat.web.controller.member;

import book.chat.domain.service.MemberService;
import book.chat.web.DTO.MemberDTO;
import book.chat.web.DTO.MemberJoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinPage(@ModelAttribute MemberJoinForm memberJoinForm){
        return "layout/member-join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute MemberJoinForm memberJoinForm,
                       BindingResult bindingResult,
                       @CookieValue(value = "idCheck", required = false) String idCheck,
                       @CookieValue(value = "emailCheck", required = false) String mailCheck){
        // todo id중복검사 메서드 만든 다음, 중복 검사 통과시 session에 uuid저장하는 방식.
        // todo 메일도 똑같이.
        if(!pwIsSame(memberJoinForm)){
            bindingResult.reject("pwDoubleCheck", null, null);
        }
        if(bindingResult.hasErrors()){
            return "layout/member-join";
        }
        memberService.save(memberJoinForm);
        return "layout/home";
    }

    public boolean pwIsSame(MemberJoinForm memberJoinForm) {
        return memberJoinForm.getPw().equals(memberJoinForm.getPw2());
    }

    @GetMapping("/{memberId}/info")
    public String memberInfo(@PathVariable("memberInfo") String id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "layout/member-info";
    }
}