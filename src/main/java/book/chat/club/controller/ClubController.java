package book.chat.club.controller;

import book.chat.api.aws.AwsS3Service;
import book.chat.board.dto.ClubBoardDTO;
import book.chat.board.service.BoardService;
import book.chat.club.dto.ClubDTO;
import book.chat.club.dto.ClubMakingForm;
import book.chat.club.service.ClubService;
import book.chat.common.SessionConst;
import book.chat.common.dto.BookDTO;
import book.chat.meeting.dto.MeetingDto;
import book.chat.meeting.service.MeetingService;
import book.chat.member.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/club")
public class ClubController {

    private final ClubService clubService;
    private final BoardService boardService;
    private final MeetingService meetingService;

    @GetMapping("/save")
    public String makingForm(@ModelAttribute ClubMakingForm makingForm) {
        return "layout/club-make";
    }

    @PostMapping(path = "/save",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String clubMaking(@Validated @ModelAttribute ClubMakingForm makingForm, BindingResult bindingResult,
                             HttpServletRequest request, Model model) {

        // 만약 클럽을 못만든다면
        if (bindingResult.hasErrors()) {
            return "layout/club-make";
        }

        MemberDTO loginMember = (MemberDTO) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
        ClubDTO clubDTO = clubService.save(makingForm, loginMember.getNo());
        model.addAttribute("club", clubDTO);
        return "layout/club-info";
    }

    @GetMapping("/list")
    public String clubList(Model model) {
        model.addAttribute("clubs", clubService.findAll());
        return "layout/club-list";
    }

    @GetMapping("")
    public String clubInfo(@RequestParam("clubNo") Long clubNo, Model model, HttpSession session) {
        ClubDTO clubDTO = clubService.findClubByNo(clubNo);
        MemberDTO memberDto = (MemberDTO)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (memberDto != null) {
            if (clubDTO.getMembers().contains(memberDto.getNo())) {
                model.addAttribute("joined", "zz");
            }
        }
        List<BookDTO> readBooksLimit10;
        if(clubDTO.getReadBooks().toString().equals("[]")|| clubDTO.getReadBooks().toString().equals("[ ]")){
            readBooksLimit10 = new ArrayList<>();
            clubDTO.setReadBooks(new ArrayList<>());
        }else {
            readBooksLimit10 = clubService.findReadBooksLimit10(clubDTO.getReadBooks());
        }


        List<ClubBoardDTO> byClubNo = boardService.findClubBoardByClubNo(clubNo);
        model.addAttribute("loginMemberNo", memberDto.getNo());
        model.addAttribute("club", clubDTO);
        model.addAttribute("boards", byClubNo);
        List<MeetingDto> notDoneMeeting = meetingService.findNotDoneMeeting(clubNo);
        model.addAttribute("meetings", notDoneMeeting);
        model.addAttribute("members", clubService.findClubMember(clubDTO));


        model.addAttribute("books", readBooksLimit10);
        return "layout/club-info";
    }

    @GetMapping("/join")
    public String joinClub(@RequestParam("clubNo") Long clubNo, HttpSession session) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        clubService.joinMember(memberDTO.getId(), clubNo);
        return "redirect:/club?clubNo=" + clubNo;
    }


    @GetMapping("/chatting")
    public String chatting(@RequestParam("clubNo") Long clubNo, HttpSession session, Model model, HttpServletResponse response) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
//        if(loginMember == null){
//            // todo
////            throw new AuthenticationException();
//        }
        if (!loginMember.getJoinClub().contains(clubNo)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "redirect:/";
        }
        model.addAttribute("clubNo", clubNo);
        return "layout/chatting";
    }
}
