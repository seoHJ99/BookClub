package book.chat.web.controller.club;

import book.chat.domain.entity.Club;
import book.chat.domain.service.ClubService;
import book.chat.web.DTO.ClubDTO;
import book.chat.web.DTO.ClubMakingForm;
import book.chat.web.DTO.MemberDTO;
import book.chat.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/info/{clubNo}")
    public String clubInfo(@PathVariable("clubNo") Long clubNo, Model model){
        model.addAttribute("club", clubService.findClubByNo(clubNo));
        return "layout/club-info";
    }
}
