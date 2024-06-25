package controller.mypage;

import data.dto.AttendanceDto;
import data.dto.MemberDto;
import data.service.AttendanceService;
import data.service.TeacherService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import data.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class MyPageTeacherController {

    @NonNull
    private TeacherService teacherService;

    @NonNull
    private AttendanceService attendanceService;

    @GetMapping("/attendancelist")
    public String attendancelist() {
        return "thymeleaf/teacher/attendanceList";
    }

    @GetMapping("/attendancedetail")
    public String attendancedetail(
            Model model
    ) {
        List<AttendanceDto> attendancelist = teacherService.getStudentAttendaceList();

        model.addAttribute("attendancelist",attendancelist);
        return "thymeleaf/teacher/attendanceDetail";
    }

    @GetMapping("/roleapprove")
    public String roleapprove(
            Model model
    ) {
        List<MemberDto> memberlist = teacherService.getStudentList();

        model.addAttribute("memberlist", memberlist);

        return "thymeleaf/teacher/roleapprove";
    }

    @PostMapping("/roleupdate")
    public String roleupdate(
            @RequestParam int member_id
    ){
        teacherService.updateTeacher(member_id);

        return "redirect:/teacher/roleapprove";
    }

    @GetMapping("/updateProfile")
    public String updateProfile() {
        return "thymeleaf/teacher/updateProfile";
    }
}
