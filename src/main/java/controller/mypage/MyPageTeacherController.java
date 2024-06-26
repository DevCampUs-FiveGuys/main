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
@RequestMapping("/teacher/mypage")
@RequiredArgsConstructor
public class MyPageTeacherController {

    @NonNull
    private TeacherService teacherService;

    @GetMapping("")
    public String attendancelist() {
        return "thymeleaf/teacher/attendanceList";
    }

    @GetMapping("/attendancedetail")
    public String attendancedetail(
            @RequestParam("date") String dateStr,
            Model model
    ) {
        List<AttendanceDto> attendancelist = teacherService.getAttendanceByDate(dateStr);

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

    @PostMapping("/rolestudent")
    public String rolestudent(
            @RequestParam int member_id
    ){
        teacherService.updateStudent(member_id);

        return "redirect:/teacher/mypage/roleapprove";
    }

    @PostMapping("/roleguest")
    public String roleguest(
            @RequestParam int member_id
    ){
        teacherService.updateGuest(member_id);

        return "redirect:/teacher/mypage/roleapprove";
    }

    @GetMapping("/updateProfile")
    public String updateProfile() {
        return "thymeleaf/teacher/updateProfile";
    }

    @GetMapping("/attendanceOnDate")
    @ResponseBody
    public List<AttendanceDto> getAttendanceByDate(
            @RequestParam("date") String dateStr
    ) {
        return teacherService.getAttendanceByDate(dateStr);
    }
}
