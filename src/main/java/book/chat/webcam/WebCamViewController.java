package book.chat.webcam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebCamViewController {

    @GetMapping("/webcam")
    public String webcam(){
        return "web";
    }
}
