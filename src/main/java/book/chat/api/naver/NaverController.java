package book.chat.api.naver;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class NaverController {

    private final BookSearchAPI bookSearchAPI;

    @GetMapping("/book")
    public String searchingBook(@RequestParam("keyword") String keyword,
                                @RequestParam int amount,
                                @RequestParam int start,
                                Model model) throws IOException, ParseException {
        model.addAttribute("bookList", bookSearchAPI.bookSearch(keyword, amount, start));
        return "ajax/fragment/board-book";
    }
}
