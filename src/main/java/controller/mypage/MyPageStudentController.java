package controller.mypage;

import data.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student_mypage")
public class MyPageStudentController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/attendance")
    public String attendance() {
        return "thymeleaf/student/attendance";
    }

    @GetMapping("/portfolio_favorites")
    public String portfolio_favorites() {
        return "thymeleaf/student/portfolio_favorites";
    }

    @GetMapping("/portfolio_posts")
    public String portfolio_posts() {
        return "thymeleaf/student/portfolio_posts";
    }

    @GetMapping("/updateProfile")
    public String updateProfile() {
        return "thymeleaf/student/updateProfile";
    }
}
