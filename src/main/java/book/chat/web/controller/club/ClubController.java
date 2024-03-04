package book.chat.web.controller.club;

import book.chat.domain.entity.Club;
import book.chat.domain.service.ClubService;
import book.chat.web.DTO.BookDTO;
import book.chat.web.DTO.ClubDTO;
import book.chat.web.DTO.ClubMakingForm;
import book.chat.web.DTO.MemberDTO;
import book.chat.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/club")
public class ClubController {

    private final ClubService clubService;

    @GetMapping("/save")
    public String makingForm(@ModelAttribute ClubMakingForm makingForm){
        return "layout/club-make";
    }

    @PostMapping("/save")
    public String clubMaking(@Validated @ModelAttribute ClubMakingForm makingForm, BindingResult bindingResult,
                             HttpServletRequest request, Model model){
        // 만약 클럽을 못만든다면
        if(bindingResult.hasErrors()){
            return "layout/club-make";
        }
        ClubDTO clubDTO = clubService.save(makingForm, (MemberDTO) request.getSession(false)
                .getAttribute(SessionConst.LOGIN_MEMBER));
        model.addAttribute("club", clubDTO);
        return "layout/club-info";
    }

    @GetMapping("/list")
    public String clubList(Model model){
        model.addAttribute("clubs", clubService.findAll());
        return "layout/club-list";
    }

    @GetMapping("")
    public String clubInfo(@RequestParam("clubNo") Long clubNo, Model model, HttpSession session){
        ClubDTO clubDTO = clubService.findClubByNo(clubNo);
        Object memberDto =  session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(memberDto != null ){
            MemberDTO memberDTO = (MemberDTO) memberDto;
            if(  clubDTO.getMembers().contains(memberDTO.getNo() )){
                model.addAttribute("joined", "zz");
            }
        }
        model.addAttribute("club", clubDTO);
        model.addAttribute("members", clubService.findClubMember(clubDTO));
        model.addAttribute("books", clubService.findReadBooksLimit10(clubDTO.getReadBooks()));
        return "layout/club-info";
    }

    @GetMapping("/join")
    public String joinClub(@RequestParam("clubNo") Long clubNo, HttpSession session){
        // todo 아이디로 현재 로그인 맴버 가져오기
        MemberDTO memberDTO =(MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        clubService.joinMember(memberDTO.getNo(), clubNo);
        return "redirect:/club?clubNo=" + clubNo;
    }

    @GetMapping("/chatting")
    public String chatting(@RequestParam("clubNo") Long clubNo, HttpSession session, Model model){
        MemberDTO loginMember = (MemberDTO)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(loginMember.getJoinClub().contains(clubNo)){
            model.addAttribute("clubNo", clubNo);
        }
        model.addAttribute("member", loginMember);
        return "layout/chatting";
    }
}
