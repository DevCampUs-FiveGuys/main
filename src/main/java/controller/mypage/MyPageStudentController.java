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

    // 출석현황 페이지로 이동
    @GetMapping("/attendance")
    public String attendance() {
        return "thymeleaf/student/attendance";
    }

    // 찜목록 페이지로 이동
    @GetMapping("/portfolio_favorites")
    public String portfolio_favorites() {
        return "thymeleaf/student/portfolio_favorites";
    }

    // 게시글 페이지로 이동
    @GetMapping("/portfolio_posts")
    public String portfolio_posts() {
        return "thymeleaf/student/portfolio_posts";
    }

    // 정보수정 페이지로 이동
    @GetMapping("/updateProfile")
    public String updateProfile() {
        return "thymeleaf/student/updateProfile";
    }

    // 입실 데이터 추가
    @PostMapping("/attendance/checkin")
    @ResponseBody
    public void insertCheckIn(@RequestParam String check_in, @RequestParam int member_id) {
        attendanceService.insertCheckIn(check_in, member_id);
    }

    // 퇴실 데이터 추가 (업데이트)
    @PostMapping("/attendance/checkout")
    @ResponseBody
    public void updateCheckOut(@RequestParam String check_out, @RequestParam int member_id) {
        attendanceService.updateCheckOut(check_out, member_id);
    }

    // 전체 출석 데이터 조회
    @GetMapping("/attendance/all")
    @ResponseBody
    public List<AttendanceDto> getAllAttendance(@RequestParam int member_id) {
        return attendanceService.getAllAttendance(member_id);
    }

    // 입실 데이터 삭제
    @DeleteMapping("/attendance/checkin")
    @ResponseBody
    public void deleteCheckIn(@RequestParam int member_id) {
        attendanceService.deleteCheckIn(member_id);
    }

    // 출석일수 조회
    @GetMapping("/attendance/days")
    @ResponseBody
    public int getAttendanceDays(@RequestParam int member_id) {
        return attendanceService.getAttendanceDays(member_id);
    }

    // 지각 데이터 업데이트
    @PostMapping("/attendance/late")
    @ResponseBody
    public void updateLate(@RequestParam int member_id) {
        attendanceService.updateLate(member_id);
    }

    // 지각일수 최댓값 조회
    @GetMapping("/attendance/late/max")
    @ResponseBody
    public int getLateDays(@RequestParam int member_id) {
        return attendanceService.getLateDays(member_id);
    }

    // 결석 데이터 업데이트
    @PostMapping("/attendance/absent")
    @ResponseBody
    public void updateAbsent(@RequestParam int member_id) {
        attendanceService.updateAbsent(member_id);
    }

    // 지각을 결석으로 업데이트
    @PostMapping("/attendance/updateAbsentBasedOnLate")
    @ResponseBody
    public void updateAbsentBasedOnLate(@RequestParam int member_id) {
        attendanceService.updateAbsentBasedOnLate(member_id);
    }

    // 결석일수 최댓값 조회
    @GetMapping("/attendance/absent/max")
    @ResponseBody
    public int getAbsentDays(@RequestParam int member_id) {
        return attendanceService.getAbsentDays(member_id);
    }

    // 휴가 데이터 추가
    @PostMapping("/attendance/vacation")
    @ResponseBody
    public void insertVacation(@RequestBody AttendanceDto attendanceDto) {
        attendanceService.insertVacation(attendanceDto);
    }

    // 병가 데이터 업데이트
    @PostMapping("/attendance/hospital")
    @ResponseBody
    public void updateHospital(@RequestParam int member_id, @RequestParam int confirm) {
        attendanceService.updateHospital(member_id, confirm);
    }
}
