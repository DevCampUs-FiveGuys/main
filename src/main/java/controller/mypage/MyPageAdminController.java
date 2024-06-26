package controller.mypage;

import data.dto.AttendanceDto;
import data.dto.MemberDto;
import data.service.AdminService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/mypage")
@RequiredArgsConstructor
public class MyPageAdminController {

    @NonNull
    private AdminService adminService;

    // 권한수정
    @GetMapping("/changerole")
    public String changeRoles(Model model){
        List<MemberDto> memberlist = adminService.getAllMemberList();
        model.addAttribute("memberlist", memberlist);

        return "thymeleaf/admin/Admchangeroles";
    }

    // 출석현황
    @GetMapping("")
    public String attendanceList(Model model) {
        List<AttendanceDto> attendancelist = adminService.getallattendance();

        model.addAttribute("attendancelist",attendancelist);
        return "thymeleaf/admin/Admattendancelist";
    }
}
