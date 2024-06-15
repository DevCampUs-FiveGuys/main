package controller.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentMyPageController {

    @GetMapping("/student")
    public String studentMyPage() {
        // Add any attributes needed for the view
        return "thymeleaf/student_mypage";
    }
}
