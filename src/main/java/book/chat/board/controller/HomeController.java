package book.chat.board.controller;

import book.chat.api.naver.BookSearchAPI;
import book.chat.board.dto.ReviewDTO;
import book.chat.board.service.BoardService;
import book.chat.club.service.ClubService;
import book.chat.member.dto.MemberDTO;
import book.chat.member.dto.MemberJoinForm;
import book.chat.member.service.MemberService;
import book.chat.redis.service.RedisService;
import book.chat.common.dto.BookDTO;
import book.chat.login.dto.LoginDto;
import com.amazonaws.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ClubService clubService;
    private final BoardService reviewService;
    private final RedisService redisService;
    private final BookSearchAPI bookSearchAPI;

    @GetMapping("/")
    public String home(Model model){
        List<ReviewDTO> rescent10Dtos = reviewService.findRecent10Review();
        List<BookDTO> bookDTOS = rescent10Dtos.stream()
                .map(dto -> bookSearchAPI.bookSearch(dto.getIsbn()).get(0))
                .collect(Collectors.toList());
        model.addAttribute("loginDto", new LoginDto());
        model.addAttribute("books", bookDTOS);
        model.addAttribute("clubs",clubService.findRecent4Club());
        model.addAttribute("boards", rescent10Dtos);
        List<String> top10PopularBooks = redisService.getTop10PopularBooks();
        List<BookDTO> collect = top10PopularBooks.stream().map(isbn -> bookSearchAPI.bookSearch(isbn).get(0)).collect(Collectors.toList());
        model.addAttribute("ranking", collect);
        return "layout/home";
    }

    @ResponseBody
    @PostMapping("/test")
    public ResponseEntity<String> test(MemberJoinForm memberJoinForm, @RequestPart("image")MultipartFile image) throws JsonProcessingException {
        ObjectMapper oj = new ObjectMapper();
        System.out.println(memberJoinForm);

        MemberDTO data = new MemberDTO();
        String s = oj.writeValueAsString(data);
        String a = "{\"aa\":\"xx\", \"data\" : \"" + s +"\"}";

//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"aa\":\"xx\"}");
//        MemberDTO memberDTO = new MemberDTO();
//        memberDTO.setId("22");
        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(a);
    }

}
