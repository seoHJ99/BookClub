package book.chat.common;


import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalTime;
import java.util.Enumeration;

@Controller
@Slf4j
//@RequiredArgsConstructor
public class TestCon extends HttpServlet {

//    private final CamSession camSession;


//    @ResponseBody
//    @GetMapping("/test")
    public String test(HttpServletRequest request, HttpSession httpSession){
//        ServletContext servletContext = request.getServletContext();
//
//        request.getSession(false).setAttribute("aaa", "sss");
//        System.out.println(httpSession.getAttribute("aaa"));
        System.out.println("TestCon.test");
        return "layout/chattingTest";
    }

    @ResponseBody
//    @GetMapping("/test2")
    public String test2(HttpSession session, HttpServletRequest request){

        return "ok";
    }
}
