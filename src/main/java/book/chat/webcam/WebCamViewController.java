package book.chat.webcam;

import book.chat.common.SessionConst;
import book.chat.meeting.dto.MeetingDto;
import book.chat.meeting.entity.Meeting;
import book.chat.meeting.service.MeetingService;
import book.chat.member.dto.MemberDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class WebCamViewController {

    private final MeetingService meetingService;
    private final Map<MeetingDto, UUID> roomIdMap = new ConcurrentHashMap<>();

    @GetMapping("/webcam")
    public String webcam(@RequestParam("meetingNo") Long meetingNo, @RequestParam("clubNo") Long clubNo,
                         HttpSession session, Model model, HttpServletResponse response) throws IOException {
        MeetingDto meetingDto = meetingService.findByClubNoAndNo(clubNo, meetingNo);
        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(!meetingDto.getJoinMember().contains(loginMember.getNo())){
             response.sendError(403);
        }

        if(!LocalDateTime.now().isBefore(meetingDto.getDateTimeAll())){
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }

        UUID roomId = null;
        if(roomIdMap.containsKey(meetingDto)){
            roomId = roomIdMap.get(meetingDto);
        }else {
            roomId = UUID.randomUUID();
            roomIdMap.put(meetingDto, roomId);
        }

        model.addAttribute("roomId", roomId);
        return "web";
    }

    @GetMapping("/test")
    public String test(){
        return "web";
    }
}
