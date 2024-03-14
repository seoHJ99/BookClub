package book.chat.club.controller;

import book.chat.club.dto.ClubDTO;
import book.chat.club.dto.ClubMakingForm;
import book.chat.club.service.ClubService;
import book.chat.member.dto.MemberDTO;
import book.chat.common.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        MemberDTO memberDTO =(MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        clubService.joinMember(memberDTO, clubNo);
        return "redirect:/club?clubNo=" + clubNo;
    }

    @GetMapping("/chatting")
    public String chatting(@RequestParam("clubNo") Long clubNo, HttpSession session, Model model, HttpServletResponse response){
        MemberDTO loginMember = (MemberDTO)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(! loginMember.getJoinClub().contains(clubNo)){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "redirect:/";
        }
        model.addAttribute("clubNo", clubNo);
        return "layout/chatting";
    }
}
