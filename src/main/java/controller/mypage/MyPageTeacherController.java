package controller.mypage;

import data.dto.AttendanceDto;
import data.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher_mypage")
public class MyPageTeacherController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/attendancelist")
    public String attendance() {
        return "thymeleaf/teacher/attendanceList";
    }

    @GetMapping("/attendancedetail")
    public String portfolio_favorites() {
        return "thymeleaf/teacher/attendanceDetail";
    }

    @GetMapping("/roleapprove")
    public String portfolio_posts() {
        return "thymeleaf/teacher/roleApprove";
    }

    @GetMapping("/updateProfile")
    public String updateProfile() {
        return "thymeleaf/teacher/updateProfile";
    }

    // 출석 기록을 조회하는 [GET] Endpoint
    @GetMapping("/attendance/{member_id}")
    @ResponseBody
    public List<AttendanceDto> getAttendance(@PathVariable int member_id) {
        return attendanceService.getAttendanceByMemberId(member_id);
    }
}
