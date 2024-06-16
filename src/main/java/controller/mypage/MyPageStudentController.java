package controller.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageStudentController {
    @GetMapping("/mypage/attendance")
    public String attendance() {
        return "thymeleaf/student/attendance";
    }
    @GetMapping("/mypage/portfolio_favorites")
    public String portfolio_favorites() {
        return "thymeleaf/student/portfolio_favorites";
    }
    @GetMapping("/mypage/portfolio_posts")
    public String portfolio_posts() {
        return "thymeleaf/student/portfolio_posts";
    }
    @GetMapping("/mypage/updateProfile")
    public String updateProfile() {
        return "thymeleaf/student/updateProfile";
    }
}
