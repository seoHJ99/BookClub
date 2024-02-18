package book.chat.web.controller.member;

import book.chat.domain.service.MemberService;
import book.chat.web.DTO.MemberDTO;
import book.chat.web.DTO.MemberJoinForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinPage(@ModelAttribute MemberJoinForm memberJoinForm){
        return "layout/member-join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute MemberJoinForm memberJoinForm){
        // bingResult 에 오류가 있으면 /join으로 리턴
        memberService.save(memberJoinForm);
        return "layout/home";
    }

    @GetMapping("/{memberId}/info")
    public String memberInfo(@PathVariable("memberInfo") String id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "layout/member-info";
    }
}