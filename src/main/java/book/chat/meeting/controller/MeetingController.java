package book.chat.meeting.controller;

import book.chat.club.service.ClubService;
import book.chat.club.dto.ClubDTO;
import book.chat.meeting.dto.MeetingDto;
import book.chat.meeting.service.MeetingService;
import book.chat.member.dto.MemberDTO;
import book.chat.common.SessionConst;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
                              HttpSession session){
        if(bindingResult.hasErrors()){
            return "layout/meeting-make";
        }

        // 클럼 맴버만 미팅 생성 가능.
        ClubDTO clubDTO = clubService.findClubByNo(meeting.getClubNo());
        MemberDTO makingMenber = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
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

    @GetMapping("/join")
    public String meetingJoin(@RequestParam(value = "no") Long no, @RequestParam(value = "clubNo") Long clubNo, Model model, HttpSession session, HttpServletResponse response) throws IOException {
        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(!loginMember.getJoinClub().contains(no)){
            response.sendError(HttpStatus.FORBIDDEN.value());
        }
        meetingService.join(loginMember, clubNo, no);
        response.setStatus(HttpStatus.OK.value());
        return "redirect:/club";
    }

    @GetMapping("/out")
    public String meetingOut(@RequestParam(value = "no") Long no, @RequestParam(value = "clubNo") Long clubNo, Model model, HttpSession session, HttpServletResponse response) throws IOException {
        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(!loginMember.getJoinClub().contains(no)){
            response.sendError(HttpStatus.FORBIDDEN.value());
        }
        meetingService.out(loginMember, clubNo, no);
        return "redirect:/club";
    }
}
