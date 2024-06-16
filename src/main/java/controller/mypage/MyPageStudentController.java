package controller.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageStudentController {
    @GetMapping("/student_mypage/attendance")
    public String attendance() {
        return "thymeleaf/student/attendance";
    }
    @GetMapping("/student_mypage/portfolio_favorites")
    public String portfolio_favorites() {
        return "thymeleaf/student/portfolio_favorites";
    }
    @GetMapping("/student_mypage/portfolio_posts")
    public String portfolio_posts() {
        return "thymeleaf/student/portfolio_posts";
    }
    @GetMapping("/student_mypage/updateProfile")
    public String updateProfile() {
        return "thymeleaf/student/updateProfile";
    }
}
