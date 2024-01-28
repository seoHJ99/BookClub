package book.chat.controller;

import book.chat.repository.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewBoardController {

    private final ReviewBoardRepository reviewBoardRepository;

    @RequestMapping("/reviews")
    public String seeBoard(Model model){
        log.info("board all= {}", reviewBoardRepository.findAll());
        model.addAttribute("boards", reviewBoardRepository.findAll());
        return "/board";
    }


}
