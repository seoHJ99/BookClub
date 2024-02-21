package book.chat.web.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestCon {

    @GetMapping("/tamplate/test")
    public String test(){
        return "layout/board-write";
    }
}
