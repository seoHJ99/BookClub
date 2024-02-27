package book.chat.web.controller.club;

import book.chat.domain.service.ClubService;
import book.chat.domain.service.MeetingService;
import book.chat.web.DTO.ClubDTO;
import book.chat.web.DTO.MeetingDto;
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
        MemberDTO makingMenber = (MemberDTO) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
        if(! clubDTO.getMembers().contains(makingMenber.getNo())){
            bindingResult.reject("Meeting.NoMember");
            return "layout/meeting-make";
        }
        meetingService.save(meeting, makingMenber);
        return "layout/club-info";
    }

    @GetMapping("/list")
    public String meetingList(@RequestParam(value = "clubNo", required = false) Long clubNo, Model model){
        if(clubNo != null){
            model.addAttribute("meetings", meetingService.findRecent10Meetings(clubNo));
        }
//        System.out.println(meetingService.findRecent10Meetings().get(0).getMeetingDate());
        model.addAttribute("meetings", meetingService.findRecent10Meetings());
        return "layout/meeting-list";
    }
}
