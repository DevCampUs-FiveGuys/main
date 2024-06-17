package com.example.devcampus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "main";
    }
    @GetMapping("/signup")
    public String signup() {
        return "thymeleaf/signup";
    }
    @GetMapping("/login")
    public String login() {
        return "thymeleaf/login";
    }
    @GetMapping("/review")
    public String review() {
        return "thymeleaf/review";
    }
    @GetMapping("/portfolio")
    public String portfolio() {
        return "thymeleaf/portfolio";
    }
//    @GetMapping("/qna")
//    public String qna() {
//        return "thymeleaf/qna";
//    }
    @GetMapping("/student_mypage")
    public String student_mypage() {
        return "thymeleaf/student/student_mypage";
    }
    @GetMapping("/portfolioDetail")
    public String portfolioDetail() {
        return "thymeleaf/portfolioDetail";
    }

    @GetMapping("/portfolio/portfolioWrite")
    public String portfolioWrite() {
        return "thymeleaf/portfolioWrite";
    }
}
