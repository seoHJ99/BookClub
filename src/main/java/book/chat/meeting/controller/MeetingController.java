package book.chat.meeting.controller;

import book.chat.club.dto.ClubDTO;
import book.chat.club.service.ClubService;
import book.chat.common.SessionConst;
import book.chat.meeting.dto.MeetingDto;
import book.chat.meeting.service.MeetingService;
import book.chat.member.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

    private final ClubService clubService;
    private final MeetingService meetingService;

    @GetMapping("/save")
    public String meetingForm(@ModelAttribute("meeting") MeetingDto meeting) {
        return "layout/meeting-make";
    }

    @PostMapping("/save")
    public String meetingMake(@RequestParam("clubNo") Long clubNo,
                              @Validated @ModelAttribute("meeting") MeetingDto meeting,
                              BindingResult bindingResult,
                              HttpSession session) {
        meeting.setClubNo(clubNo);
        MemberDTO makingMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (bindingResult.hasErrors()) {
            return "layout/meeting-make";
        }

        // 클럼 맴버만 미팅 생성 가능.
        if (! makingMember.getJoinClub().contains(clubNo)) {
            bindingResult.reject("Meeting.NoMember");
            return "layout/meeting-make";
        }
        meeting.setJoinMember(new ArrayList<>());
        meetingService.save(meeting, makingMember);
        return "redirect:/club?clubNo="+ clubNo;
    }

    @GetMapping("/list")
    public String meetingList(@RequestParam(value = "clubNo", required = false) Long clubNo, Model model, HttpSession session) {
        MemberDTO memberDto = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (clubNo != null) {
            model.addAttribute("meetings", meetingService.findRecent10Meetings(clubNo));
        }
        model.addAttribute("loginMember2", memberDto.getNo());
        model.addAttribute("meetings", meetingService.findRecent10Meetings());
        return "layout/meeting-list";
    }

    @ResponseBody
    @GetMapping("/join")
    public String meetingJoin(@RequestParam(value = "no") Long no, @RequestParam(value = "clubNo") Long clubNo,
                              HttpSession session,
                              HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        String url = request.getRequestURI();
        if (!loginMember.getJoinClub().contains(clubNo)) {
            System.out.println("xxxxxxxxxx");
            response.sendError(HttpStatus.FORBIDDEN.value());
            return "<script>" +
                    "alter('모임 회원이 아닙니다.')"+
                    "location.href='" + request.getRequestURL() + "';</script>";
        }
        boolean success = meetingService.join(loginMember, clubNo, no);
        if (!success) {
            return "<script>alert('정원이 가득 찼습니다.');" +
                    "location.href='/';</script>";
        }
        return "<script>location.href='/club?clubNo=" + clubNo + "';</script>";
    }

    @ResponseBody
    @GetMapping("/out")
    public String meetingOut(@RequestParam(value = "no") Long no,
                             @RequestParam(value = "clubNo") Long clubNo,
                             HttpSession session,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (!loginMember.getJoinClub().contains(clubNo)) {
            System.out.println(loginMember.getJoinClub());
            System.out.println(clubNo);
            System.out.println("ddddddddddddddddddd");
            response.sendError(HttpStatus.FORBIDDEN.value());
            return "<script>" +
                    "alter('모임 회원이 아닙니다.')"+
                    "location.href='" + request.getRequestURL() + "';</script>";
        }

        meetingService.out(loginMember, clubNo, no);
        return "<script>location.href='/club?clubNo=" + clubNo + "';</script>";
    }

//    @GetMapping("/start")
//    public String camMeetingStart(@RequestParam(value = "no") Long no,
//                                  @RequestParam(value = "clubNo") Long clubNo,
//                                  Model model,
//                                  HttpSession session,
//                                  HttpServletResponse response) throws IOException {
//        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
//        MeetingDto meetingDto = meetingService.findByClubNoAndNo(clubNo, no);
//        if(!meetingDto.isOnline()){
//            response.sendError(HttpStatus.FORBIDDEN.value());
//        }
//        return "redirect:/webcam?meetingNo="+no+"&clubNo="+ clubNo;
//    }
}
