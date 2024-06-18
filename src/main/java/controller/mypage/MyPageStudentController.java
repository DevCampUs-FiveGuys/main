package controller.mypage;

import data.dto.MyPageDto;
import data.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyPageStudentController {
    @Autowired
    MyPageService myPageService;

    @GetMapping("/student_mypage/attendance")
    public String attendance() {
        return "thymeleaf/student/attendance";
    }

    @PostMapping("/student_mypage/attendance/check_in")
    @ResponseBody
    public String checkIn(@RequestBody MyPageDto dto) {
        myPageService.saveCheckIn(dto);
        return "입실이 성공적으로 처리되었습니다.";
    }

    @PostMapping("/student_mypage/attendance/check_out")
    @ResponseBody
    public String checkOut(@RequestBody MyPageDto dto) {
        myPageService.saveCheckOut(dto);
        return "퇴실이 성공적으로 처리되었습니다.";
    }

    @PostMapping("/student_mypage/attendance/vacation")
    @ResponseBody
    public String applyVacation(@RequestBody MyPageDto dto) {
        myPageService.applyVacation(dto);
        return "휴가 신청이 성공적으로 처리되었습니다.";
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
