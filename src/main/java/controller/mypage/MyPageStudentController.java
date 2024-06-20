package controller.mypage;

import data.dto.AttendanceDto;
import data.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 출석 기록을 조회하는 [GET] Endpoint
    @GetMapping("/attendance/{member_id}")
    @ResponseBody
    public List<AttendanceDto> getAttendance(@PathVariable int member_id) {
        return attendanceService.getAttendanceByMemberId(member_id);
    }

    // 출석 기록을 추가하는 [POST] Endpoint
    @PostMapping("/attendance")
    @ResponseBody
    public void insertAttendance(@RequestBody AttendanceDto attendance) {
        attendanceService.insertAttendance(attendance);
    }

    // 출석 기록을 수정하는 [PUT] Endpoint
    @PutMapping("/attendance")
    @ResponseBody
    public void updateAttendance(@RequestBody AttendanceDto attendance) {
        attendanceService.updateAttendance(attendance);
    }

    // 출석 기록을 삭제하는 [DELETE] Endpoint
    @DeleteMapping("/attendance/{attendance_id}")
    @ResponseBody
    public void deleteAttendance(@PathVariable int attendance_id) {
        attendanceService.deleteAttendance(attendance_id);
    }
}
