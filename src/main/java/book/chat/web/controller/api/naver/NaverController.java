package book.chat.web.controller.api.naver;

import book.chat.domain.api.naver.BookSearchAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class NaverController {

    private final BookSearchAPI bookSearchAPI;

    // html로 리턴
    @GetMapping("/book")
    public String searchingBook(@RequestParam("keyword") String keyword,
                                @RequestParam int amount,
                                @RequestParam int start,
                                Model model) throws IOException {
        model.addAttribute("bookList", bookSearchAPI.bookSearch(keyword, amount, start));
        return "board-book";
    }
}
