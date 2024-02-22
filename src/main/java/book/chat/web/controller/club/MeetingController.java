package book.chat.web.controller.club;

import book.chat.domain.service.ClubService;
import book.chat.domain.service.MeetingService;
import book.chat.web.DTO.ClubDTO;
import book.chat.web.DTO.MeetingDto;
import book.chat.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

    private final ClubService clubService;
    private final MeetingService meetingService;

    @GetMapping("/save")
    public String meetingForm(@ModelAttribute("meeting") MeetingDto meeting){
        return "layout/meeting-make";
    }

    @PostMapping("/save")
    public String meetingMake(@Validated @ModelAttribute("meeting") MeetingDto meeting,
                              BindingResult bindingResult,
                              HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "layout/meeting-make";
        }

        // 클럼 맴버만 미팅 생성 가능.
        ClubDTO clubDTO = clubService.findClubByNo(meeting.getClubNo());
        if(! clubDTO.getMembers().contains(request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER))){
            bindingResult.reject("Meeting.NoMember");
            return "layout/meeting-make";
        }
        meetingService.save(meeting);
        return "layout/club-info";
    }
}
