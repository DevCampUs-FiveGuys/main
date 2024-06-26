package controller.mypage;

import data.dto.AttendanceDto;
import data.dto.MemberDto;
import data.service.AttendanceService;
import data.service.MemberService;
import data.service.TeacherService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import data.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher/mypage")
@RequiredArgsConstructor
public class MyPageTeacherController {

    @NonNull
    private TeacherService teacherService;

    @NonNull
    private MemberService memberService;

    @GetMapping("")
    public String attendancelist(Model model) {

        model.addAttribute("page", "attendanceList");

        return "thymeleaf/teacher/attendanceList";
    }

    @GetMapping("/attendancedetail")
    public String attendancedetail(
            @RequestParam("date") String dateStr,
            Model model
    ) {
        List<AttendanceDto> attendancelist = teacherService.getAttendanceByDate(dateStr);

        model.addAttribute("page", "attendanceDetail");

        model.addAttribute("attendancelist",attendancelist);
        return "thymeleaf/teacher/attendanceDetail";
    }

    @GetMapping("/roleapprove")
    public String roleapprove(
            Model model
    ) {
        List<MemberDto> memberlist = teacherService.getStudentList();

        model.addAttribute("page", "roleApprove");

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
    public String updateProfile(Authentication authentication, Model model) {
        if (authentication != null) {
            String email = authentication.getName();
            MemberDto member = memberService.findByUsername(email);
            model.addAttribute("member", member);
            model.addAttribute("page", "updateProfile");
        }
        return "thymeleaf/teacher/updateProfile";
    }

    @PostMapping("/update")
    public String updateInfo(@ModelAttribute MemberDto memberDto, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            MemberDto member = memberService.findByUsername(email);
            member.setTel(memberDto.getTel());
            memberService.updateMember(member);
        }
        return "redirect:/teacher/mypage/updateProfile";
    }

    @GetMapping("/attendanceOnDate")
    @ResponseBody
    public List<AttendanceDto> getAttendanceByDate(
            @RequestParam("date") String dateStr
    ) {
        return teacherService.getAttendanceByDate(dateStr);
    }

    @GetMapping("/attendanceCounts")
    @ResponseBody
    public Map<String, Integer> getAttendanceCounts() {
        return teacherService.getAttendanceCountsByDate();
    }
}
