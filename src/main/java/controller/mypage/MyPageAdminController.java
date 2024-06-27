package controller.mypage;

import data.dto.AttendanceDto;
import data.dto.CourseDto;
import data.dto.MemberDto;
import data.dto.ReviewDto;
import data.service.AdminService;
import data.service.AttendanceService;
import data.service.MemberService;
import data.service.ReviewService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/mypage")
@RequiredArgsConstructor
public class MyPageAdminController {

    @NonNull
    private AdminService adminService;
    @NonNull
    private ReviewService reviewService;
    @NonNull
    private MemberService memberService;


    // 출석현황
    @GetMapping("")
    public String attendanceList(Model model) {
        List<AttendanceDto> attendancelist = adminService.getallattendance();
        List<CourseDto> courselist = reviewService.getAllCourseList();


        model.addAttribute("courselist", courselist);
        model.addAttribute("attendancelist", attendancelist);
        return "thymeleaf/admin/Admattendancelist";
    }

    // 권한수정
    @GetMapping("/changerole")
    public String changeRoles(Model model) {

        List<MemberDto> memberlist = adminService.getAllMemberList();
        List<CourseDto> courselist = reviewService.getAllCourseList();

        model.addAttribute("memberlist", memberlist);
        model.addAttribute("courselist", courselist);

        return "thymeleaf/admin/Admchangeroles";
    }

    // 권한수정 : 권한 update 승인
    @PostMapping("/updateroles")
    public String updateRoles(@RequestParam("roles") String roles, @RequestParam("member_id") int member_id) {
        System.out.println(roles);
        System.out.println(member_id);

        adminService.updateRole(roles, member_id);

        System.out.println(roles);
        System.out.println(member_id);
        return "redirect:/admin/mypage/changerole";
    }

    // 권한수정 : 거절 버튼 (ROLE_DENT)
    @PostMapping("/roledeny")
    public String roledeny(
            @RequestParam int member_id
    ) {
        adminService.roledeny(member_id);

        return "redirect:/admin/mypage/changerole";
    }

    // 과정명 선택 -> 해당하는 기수명
    @GetMapping("/list/names")
    @ResponseBody
    public List<String> getCourseNums(@RequestParam("name") String name) {
        return reviewService.getNumOfCourse(name);
    }

    // 기수명 선택 -> 해당 맴버 출력
    @GetMapping("/list/nums")
    @ResponseBody
    public List<MemberDto> selectAllMember(@RequestParam("name") String name, @RequestParam("num") String num, @RequestParam(required = false) String roles, Model model) {

        if ("ALL".equals(roles)) {
            // roles가 null이면 모든 역할을 포함하는 로직
            return adminService.selectAllMember(name, num); // 모든 역할 포함
        } else {
            // 특정 역할만 포함하는 로직
            return adminService.selectRoleMember(name, num, roles); // 모든 역할 포함
        }
    }

    @GetMapping("/list/role")
    @ResponseBody
    public List<MemberDto> selectRole(@RequestParam(required = false) String roles) {
        if ("ALL".equals(roles)) {
            // roles가 null이면 모든 역할을 포함하는 로직
            return adminService.getAllMemberList(); // 모든 역할 포함
        }
        return adminService.selectRole(roles);
    }

    // 현재 멤버의 프로필 정보
    @GetMapping("/updateprofile")
    public String updateProfile(Authentication authentication, Model model) {
        if (authentication != null) {
            String email = authentication.getName();
            MemberDto member = memberService.findByUsername(email);
            model.addAttribute("member", member);
            model.addAttribute("page", "updateProfile");
        }
        return "thymeleaf/admin/UpdateProfile";
    }

    // 프로필 수정 후 업데이트
    @PostMapping("/update")
    public String updateInfo(@ModelAttribute MemberDto memberDto, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            MemberDto member = memberService.findByUsername(email);
            member.setTel(memberDto.getTel());
            memberService.updateMember(member);
        }
        return "redirect:/admin/mypage/updateprofile";
    }

    // 기수명 선택 -> 해당 멤버 출석 목록
    @GetMapping("/list/nums/attendance")
    @ResponseBody
    public List<AttendanceDto> selectAllMember(@RequestParam("name") String name, @RequestParam("num") String num) {

        List<AttendanceDto> selectedAttendance = adminService.selectallattendance(name, num);

        return selectedAttendance;
    }
}

